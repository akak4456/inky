package org.akak4456.service;

public interface AdminService {
	public boolean upgradeToAdminById(String id);
	public boolean removeFromAdminById(String id);
	public boolean blockUser(String id);
	public boolean unblockUser(String id);
}
