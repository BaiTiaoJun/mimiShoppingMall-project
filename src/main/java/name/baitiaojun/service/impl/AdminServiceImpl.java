package name.baitiaojun.service.impl;

import name.baitiaojun.dao.AdminDao;
import name.baitiaojun.domain.Admin;
import name.baitiaojun.domain.AdminExample;
import name.baitiaojun.service.AdminService;
import name.baitiaojun.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public Admin login(String name, String pwd) {
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminDao.selectByExample(example);
        if (list.size() > 0) {
            Admin admin = list.get(0);
            if (pwd != null && !pwd.equals("")) {
                String encryptionPwd = MD5Util.getMD5(pwd);
                if (admin.getaPass().equals(encryptionPwd)) {
                    return admin;
                }
            }
        }
        return null;
    }
}
