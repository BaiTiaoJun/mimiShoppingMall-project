package name.baitiaojun.service;

import com.github.pagehelper.PageInfo;
import name.baitiaojun.domain.ProductInfo;
import name.baitiaojun.domain.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> selectProductInfoList();

    PageInfo<ProductInfo> splitPage(int pageNum, int pageSize);

    int insertProductInfo(ProductInfo productInfo);

    ProductInfo selectProductInfoByID(int pid);

    int updateProductInfo(ProductInfo productInfo);

    int deleteProductInfo(Integer pid);

    int deleteBatch(String pid);

    PageInfo<ProductInfo> splitPageByCondition(ProductInfoVo productInfoVo, int pageSize);
}
