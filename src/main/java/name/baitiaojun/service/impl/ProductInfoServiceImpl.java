package name.baitiaojun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import name.baitiaojun.dao.ProductInfoDao;
import name.baitiaojun.domain.ProductInfo;
import name.baitiaojun.domain.ProductInfoExample;
import name.baitiaojun.domain.vo.ProductInfoVo;
import name.baitiaojun.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public List<ProductInfo> selectProductInfoList() {
        return productInfoDao.selectByExample(null);
    }

    @Override
    public PageInfo<ProductInfo> splitPage(int pageNum, int pageSize) {
        if (pageNum == 0) {
            PageHelper.startPage(pageNum + 1, pageSize);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }
        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");
        List<ProductInfo> list = productInfoDao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public int insertProductInfo(ProductInfo productInfo) {
        return productInfoDao.insert(productInfo);
    }

    @Override
    public ProductInfo selectProductInfoByID(int pid) {
        return productInfoDao.selectByPrimaryKey(pid);
    }

    @Override
    public int updateProductInfo(ProductInfo productInfo) {
        return productInfoDao.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteProductInfo(Integer pid) {
        return productInfoDao.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String pid) {
        String[] pids = pid.split(",");
        return productInfoDao.deleteBatch(pids);
    }

    @Override
    public PageInfo<ProductInfo> splitPageByCondition(ProductInfoVo productInfoVo, int pageSize) {
        int pageNum = productInfoVo.getPage();
        if (pageNum == 0) {
            PageHelper.startPage(pageNum + 1, pageSize);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<ProductInfo> list = productInfoDao.selectCondition(productInfoVo);
        return new PageInfo<>(list);
    }
}