package org.akak4456.service;

import org.akak4456.error.IdNotExist;

public interface AdminService {
	public void upgradeToAdminById(String id) throws IdNotExist;
	public void removeFromAdminById(String id) throws IdNotExist;
	public void blockUser(String id) throws IdNotExist;
	public void unblockUser(String id) throws IdNotExist;
}
