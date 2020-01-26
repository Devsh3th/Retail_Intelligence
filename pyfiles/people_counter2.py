# USAGE
# To read and write back out to video:
# python people_counter1.py --prototxt mobilenet_ssd/MobileNetSSD_deploy.prototxt \
#	--model mobilenet_ssd/MobileNetSSD_deploy.caffemodel --input videos/example_01.mp4 \
#	--output output/output_01.avi
#

# import the necessary packages
import smtplib
import time
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

import pymysql
import cv2
import dlib
# import argparse
import imutils
import numpy as np
from datetime import date, datetime
from flask import Flask, jsonify, request
from imutils.video import FPS
from imutils.video import VideoStream

from pyimagesearch.centroidtracker import CentroidTracker
from pyimagesearch.trackableobject import TrackableObject

app = Flask(__name__)

a = []

increaseQueue = []


@app.route('/', methods=['get'])
def fn1():
    userName = request.args.get('userName')

    print(userName)

    print("in url")

    queue,queueCount = fn2(userName)

    print("queue=", queue)
    print(type(queue))
    print("queueCount=",queueCount)
    print(type(queueCount))
    response = jsonify({'queueCount':queueCount,'queue':queue})

    print("response=", response)

    queue1 = str(queue)
    queueCount1 = str(queueCount)
    today_date = str(date.today())
    today_time = str(datetime.time(datetime.now()))

    print("today_date is =================================",today_date)
    print("today_time is =================================", today_time)
    print("queue====================",queue1)
    print("queueCount===================",queueCount1)

    connection = pymysql.connect(
        host='localhost',
        user='root',
        password='root',
        db='projectdb'
    )
    cursor1 = connection.cursor()
    cursor1.execute(
                "INSERT INTO detection_table(queue,queueCount,detection_date,detection_time) VALUES ('"+queue1+"','"+queueCount1+"','"+today_date+"','"+today_time+"')" )

    connection.commit()

    cursor1.close()

    connection.close()

    response.headers.add('Access-Control-Allow-Origin', '*')

    return response


@app.route('/showResult')
def fn2(userName):
    Prototxt = 'C:\\Users\\Kanchi\\PycharmProjects\\demo\\mobilenet_ssd\\MobileNetSSD_deploy.prototxt'
    model = 'C:\\Users\\Kanchi\\PycharmProjects\\demo\\mobilenet_ssd\\MobileNetSSD_deploy.caffemodel'
    Input = 'test\\test8.mp4'
    output = 'output\\output_01.avi'
    Confidence = 0.85
    skip_frames = 2
    info = []
    rightList = []
    queueCount=0

    # construct the argument parse and parse the arguments
    # ap = argparse.ArgumentParser()
    # ap.add_argument("-p", "--prototxt", required=True,
    #                 help="path to Caffe 'deploy' prototxt file")
    # ap.add_argument("-m", "--model", required=True,
    #                 help="path to Caffe pre-trained model")
    # ap.add_argument("-i", "--input", type=str,
    #                 help="path to optional input video file")
    # ap.add_argument("-o", "--output", type=str,
    #                 help="path to optional output video file")
    # ap.add_argument("-c", "--confidence", type=float, default=0.4,
    #                 help="minimum probability to filter weak detections")
    # ap.add_argument("-s", "--skip-frames", type=int, default=30,
    #                 help="# of skip frames between detections")
    # args = vars(ap.parse_args())

    # initialize the list of class labels MobileNet SSD was trained to
    # detect
    CLASSES = ["background", "aeroplane", "bicycle", "bird", "boat",
               "bottle", "bus", "car", "cat", "chair", "cow", "diningtable",
               "dog", "horse", "motorbike", "person", "pottedplant", "sheep",
               "sofa", "train", "tvmonitor"]

    # load our serialized model from disk
    print("[INFO] loading model...")
    net = cv2.dnn.readNetFromCaffe(Prototxt, model)

    # if a video path was not supplied, grab a reference to the webcam
    if not Input:
        print("[INFO] starting video stream...")
        vs = VideoStream(src=0).start()
        time.sleep(2.0)

    # otherwise, grab a reference to the video file
    else:
        print("[INFO] opening video file...")
        vs = cv2.VideoCapture(Input)

    # initialize the video writer (we'll instantiate later if need be)
    writer = None

    # initialize the frame dimensions (we'll set them as soon as we read
    # the first frame from the video)
    W = None
    H = None

    # instantiate our centroid tracker, then initialize a list to store
    # each of our dlib correlation trackers, followed by a dictionary to
    # map each unique object ID to a TrackableObject
    # ct = CentroidTracker(maxDisappeared=40, maxDistance=50)
    ct = CentroidTracker(maxDisappeared=20, maxDistance=100)

    trackers = []
    trackableObjects = {}

    # initialize the total number of frames processed thus far, along
    # with the total number of objects that have moved either up or down
    totalFrames = 0
    totalDown = 0
    totalUp = 0
    right = 0
    people = 0
    # start the frames per second throughput estimator
    fps = FPS().start()

    # loop over frames from the video stream
    while True:

        # grab the next frame and handle if we are reading from either
        # VideoCapture or VideoStream
        frame = vs.read()
        frame = frame[1] if Input else frame

        # if we are viewing a video and we did not grab a frame then we
        # have reached the end of the video
        if Input is not None and frame is None:
            break

        # resize the frame to have a maximum width of 500 pixels (the
        # less data we have, the faster we can process it), then convert
        # the frame from BGR to RGB for dlib
        frame = imutils.resize(frame, width=500)
        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        # if the frame dimensions are empty, set them
        if W is None or H is None:
            (H, W) = frame.shape[:2]
        # if we are supposed to be writing a video to disk, initialize
        # the writer
        if output is not None and writer is None:
            fourcc = cv2.VideoWriter_fourcc(*"MJPG")
            writer = cv2.VideoWriter(output, fourcc, 30,
                                     (W, H), True)

        # initialize the cur	rent status along with our list of bounding
        # box rectangles returned by either (1) our object detector or
        # (2) the correlation trackers
        status = "Waiting"
        rects = []

        # check to see if we should run a more computationally expensive
        # object detection method to aid our tracker
        if totalFrames % skip_frames == 0:
            # set the status and initialize our new set of object trackers
            status = "Detecting"
            trackers = []

            # convert the frame to a blob and pass the blob through the
            # network and obtain the detections
            blob = cv2.dnn.blobFromImage(frame, 0.007843, (W, H), 127.5)
            net.setInput(blob)
            detections = net.forward()

            # loop over the detections
            for i in np.arange(0, detections.shape[2]):
                # extract the confidence (i.e., probability) associated
                # with the prediction
                confidence = detections[0, 0, i, 2]

                # filter out weak detections by requiring a minimum
                # confidence
                if confidence > Confidence:
                    # extract the index of the class label from the
                    # detections list
                    idx = int(detections[0, 0, i, 1])

                    # if the class label is not a person, ignore it
                    if CLASSES[idx] != "person":
                        continue

                    # compute the (x, y)-coordinates of the bounding box
                    # for the object
                    box = detections[0, 0, i, 3:7] * np.array([W, H, W, H])
                    (startX, startY, endX, endY) = box.astype("int")

                    # construct a dlib rectangle object from the bounding
                    # box coordinates and then start the dlib correlation
                    # tracker
                    tracker = dlib.correlation_tracker()

                    rect = dlib.rectangle(startX, startY, endX, endY)

                    tracker.start_track(rgb, rect)

                    # add the tracker to our list of trackers so we can
                    # utilize it during skip frames
                    trackers.append(tracker)

        # otherwise, we should utilize our object *trackers* rather than
        # object *detectors* to obtain a higher frame processing throughput
        else:
            # loop over the trackers

            for tracker in trackers:
                # set the status of our system to be 'tracking' rather
                # than 'waiting' or 'detecting'
                status = "Tracking"

                # update the tracker and grab the updated position
                tracker.update(rgb)
                pos = tracker.get_position()

                # unpack the position object
                startX = int(pos.left())
                startY = int(pos.top())
                endX = int(pos.right())
                endY = int(pos.bottom())

                if (startX + endX) / 2 > 2 * W // 3:
                    cv2.rectangle(frame, (startX, startY), (endX, endY), (0, 255, 0), 2)
                    # x1 = int((startX + endX) / 2)
                    # y1 = int((startY + endY) / 2)

                    # add the bounding box coordinates to the rectangles list
                rects.append((startX, startY, endX, endY))

        # draw a horizontal line in the center of the frame -- once an
        # object crosses this line we will determine whether they were
        # moving 'up' or 'down'
        cv2.line(frame, (2 * W // 3, 0), (2 * W // 3, H), (0, 255, 255), 2)

        # use the centroid tracker to associate the (1) old object
        # centroids with (2) the newly computed object centroids
        objects = ct.update(rects)
        right = 0
        total = 0
        for i in objects.items():
            total += 1
            if i[1][0] > 2 * W // 3:
                right += 1

        people += total

        # loop over the tracked objects

        for (objectID, centroid) in objects.items():
            # check to see if a trackable object exists for the current
            # object ID
            to = trackableObjects.get(objectID, None)

            # if there is no existing trackable object, create one
            if to is None:
                to = TrackableObject(objectID, centroid)


            # otherwise, there is a trackable object so we can utilize it
            # to determine direction
            else:
                # the difference between the y-coordinate of the *current*
                # centroid and the mean of *previous* centroids will tell
                # us in which direction the object is moving (negative for
                # 'up' and positive for 'down')
                x = [c[0] for c in to.centroids]

                # direction = centroid[0] - np.mean(x)
                to.centroids.append(centroid)
                # print(x)

                # check to see if the object has been counted or not
                if not to.counted:
                    # if the direction is negative (indicating the object
                    # is moving up) AND the centroid is above the center
                    # line, count the object
                    # if direction < 0 and centroid[1] < 2*H // 3:
                    # 	totalUp += 1
                    # 	to.counted = True

                    # if the direction is positive (indicating the object
                    # is moving down) AND the centroid is below the
                    # center line, count the object
                    # if centroid[1] < 2*H // 3:
                    # 	totalUp += 1
                    # 	to.counted = True
                    #
                    # if centroid[1] > 2*H // 3:
                    # 	totalDown += 1
                    # 	to.counted = True
                    # if centroid[0] < 2 * H // 3:
                    # 	totalUp += 1
                    # 	to.counted = True

                    if centroid[0] > 2 * W // 3:
                        totalDown += 1
                        to.counted = True

                # print(len(x))



                # print(x[len(x) - 1])
                # print(x[len(x) - 2])
                if x[len(x) - 2] > (2 * W // 3) and x[len(x) - 1] < (2 * W // 3):
                    print("------------------------------------------------subtracted")
                    totalDown -= 1
                    to.counted = True

            #
            # if x1 > 2 * H // 3:
            # 	totalDown += 1
            # to.counted = True

            # store the trackable object in our dictionary
            trackableObjects[objectID] = to

            # draw both the ID of the object and the centroid of the
            # object on the output frame
            text = "ID {}".format(objectID)
            cv2.putText(frame, text, (centroid[0] - 10, centroid[1] - 10),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
            cv2.circle(frame, (centroid[0], centroid[1]), 4, (0, 255, 0), -1)

        # construct a tuple of information we will be displaying on the
        # frame
        info = [
            ("Total", total),
            ("Right", right),
            ("Status", status)

        ]

        # loop over the info tuples and draw them on our frame
        for (i, (k, v)) in enumerate(info):
            text = "{}: {}".format(k, v)
            cv2.putText(frame, text, (10, H - ((i * 20) + 20)),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 2)

        # check to see if we should write the frame to disk
        if writer is not None:
            writer.write(frame)

        # show the output frame
        cv2.imshow("Frame", frame)

        rightCount = info[1][1]

        if rightCount==0:
            queueCount+=1
            print('queueCount=',queueCount)

        if len(rightList) == 0:

            if rightCount != 0:

                rightList.append(rightCount)

                fromaddr = "retail.intel01@gmail.com"

                toaddr = userName

                msg = MIMEMultipart()

                msg['From'] = fromaddr

                msg['To'] = toaddr

                msg['Subject'] = "QUEUE NEEDS TO BE MANAGED!"

                msg.attach(MIMEText("Over Crowded persons for queue are " + str(rightCount), 'plain'))

                server = smtplib.SMTP('smtp.gmail.com', 587)

                server.starttls()

                server.login(fromaddr, "Retail123")

                text = msg.as_string()

                server.sendmail(fromaddr, toaddr, text)

                server.quit()
        else:

            if rightCount != 0:

                if rightList[-1] == rightCount:

                    pass

                else:

                    rightList.append(rightCount)

                    fromaddr = "retail.intel01@gmail.com"

                    toaddr = userName

                    msg = MIMEMultipart()

                    msg['From'] = fromaddr

                    msg['To'] = toaddr

                    msg['Subject'] = "QUEUE NEEDS TO BE MANAGED!"

                    msg.attach(MIMEText("Over Crowded persons for queue are " + str(rightCount), 'plain'))

                    server = smtplib.SMTP('smtp.gmail.com', 587)

                    server.starttls()

                    server.login(fromaddr, "Retail123")

                    text = msg.as_string()

                    server.sendmail(fromaddr, toaddr, text)

                    server.quit()

        key = cv2.waitKey(1) & 0xFF

        # if the `q` key was pressed, break from the loop
        if key == ord("q"):
            break

        # increment the total number of frames processed thus far and
        # then update the FPS counter
        totalFrames += 1
        fps.update()

    queue = people // totalFrames
    # stop the timer and display FPS information
    fps.stop()
    print("[INFO] Total Frames: ", totalFrames)
    print("[INFO] elapsed time: {:.2f}".format(fps.elapsed()))
    print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))
    print("[INFO] Average Queue: ", queue)

    # check to see if we need to release the video writer pointer
    if writer is not None:
        writer.release()

    # if we are not using a video file, stop the camera video stream
    if not Input:
        vs.stop()

    # otherwise, release the video file pointer
    else:
        vs.release()

    # close any open windows
    cv2.destroyAllWindows()

    # info.append(('queue',queue))

    print("rightList====", rightList)

    return queue,queueCount

app.run(port=8989)
