package name.baitiaojun.service.impl;

import name.baitiaojun.dao.ProductTypeDao;
import name.baitiaojun.domain.ProductType;
import name.baitiaojun.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeDao productTypeDao;

    @Override
    public List<ProductType> selectProductTypeList() {
        return productTypeDao.selectByExample(null);
    }
}
