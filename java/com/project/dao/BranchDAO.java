package com.project.dao;

import java.util.List;

import com.project.vo.BranchVO;
import com.project.vo.LocationVO;

public interface BranchDAO {
	public void insertBranch(BranchVO branchVO);
	public List viewBranch();
	public List deleteBranch(BranchVO branchVO);
	public List editBranch(BranchVO branchVO);
	public void updateBranch(BranchVO branchVO);
	public List fetchBranch(BranchVO branchVO);
}
