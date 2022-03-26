package name.baitiaojun.service;

import name.baitiaojun.domain.Admin;

public interface AdminService {
    Admin login(String name, String pwd);
}
