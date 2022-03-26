package name.baitiaojun.web.controller;

import com.github.pagehelper.PageInfo;
import com.mysql.cj.PreparedQuery;
import name.baitiaojun.domain.ProductInfo;
import name.baitiaojun.domain.vo.ProductInfoVo;
import name.baitiaojun.service.ProductInfoService;
import name.baitiaojun.utils.FileNameUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping(value = "/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    public static final int PAGE_SIZE = 5;

    private String saveFileName;

    @RequestMapping("/splitPage.action")
    private ModelAndView splitPage(HttpSession session) {
        PageInfo<ProductInfo> pageInfo;
        Object obj = session.getAttribute("vo");
        if (obj != null) {
            pageInfo = productInfoService.splitPageByCondition((ProductInfoVo) obj, PAGE_SIZE);
            session.removeAttribute("vo");
        } else {
            pageInfo = productInfoService.splitPage(1, PAGE_SIZE);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("info", pageInfo);
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @RequestMapping( "/ajaxsplit.action")
    @ResponseBody
    private void ajaxSplit(HttpSession session, ProductInfoVo productInfoVo) {
        PageInfo<ProductInfo> pageInfo = productInfoService.splitPageByCondition(productInfoVo, PAGE_SIZE);
        session.setAttribute("info", pageInfo);
    }

    @ResponseBody
    @RequestMapping("/ajaxImg.action")
    private Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成文件名UUID+上传图片名后缀
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //文件转存到tomcat服务器的指定路径下
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端json对象，封装图片路径，为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    @RequestMapping("/save.action")
    private String insertProductInfo(HttpServletRequest request, ProductInfo productInfo) {
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());
        int n = productInfoService.insertProductInfo(productInfo);

        if (n > 0) {
            request.setAttribute("msg", "商品添加成功！");
        } else {
            request.setAttribute("msg", "商品添加失败！");
        }
        saveFileName = "";
        return "forward:/productInfo/splitPage.action";
    }

    @RequestMapping("/update.action")
    private String update(ProductInfoVo productInfoVo, Model model, int pid, HttpSession session) {
        ProductInfo productInfo = productInfoService.selectProductInfoByID(pid);
        model.addAttribute("prod", productInfo);
        session.setAttribute("vo", productInfoVo);
        return "update";
    }

    @RequestMapping("/updateProductInfo.action")
    private String updateProductInfo(ProductInfo productInfo, Model model) {
        if (!saveFileName.equals("")) {
            productInfo.setpImage(saveFileName);
        }
        int n = productInfoService.updateProductInfo(productInfo);
        if (n > 0) {
            model.addAttribute("msg", "更新成功");
        } else {
            model.addAttribute("msg", "更新失败");
        }
        saveFileName = "";
        return "forward:/productInfo/splitPage.action";
    }

    @RequestMapping(value = "/delete.action", produces = "text/html;charset=utf-8")
    @ResponseBody
    private String deleteProductInfo(Integer pid, HttpSession session, ProductInfoVo productInfoVo) {
        int n = -1;
        if (pid != null) {
            n = productInfoService.deleteProductInfo(pid);
        }

        if (n > 0) {
            PageInfo<ProductInfo> pageInfo;
            if (productInfoVo != null) {
                pageInfo = productInfoService.splitPageByCondition(productInfoVo, PAGE_SIZE);
            } else {
                pageInfo = productInfoService.splitPage(1, PAGE_SIZE);
            }
            session.setAttribute("info", pageInfo);
            return "删除操作成功";
        } else {
            return "删除操作失败";
        }
    }

    @RequestMapping(value = "/deletebatch.action", produces = "text/html;charset=utf-8")
    @ResponseBody
    private String deleteBatch(String str, HttpSession session, ProductInfoVo productInfoVo) {
        int n = productInfoService.deleteBatch(str);

        if (n > 0) {
            PageInfo<ProductInfo> pageInfo;
            if (productInfoVo != null) {
                pageInfo = productInfoService.splitPageByCondition(productInfoVo, PAGE_SIZE);
            } else {
                pageInfo = productInfoService.splitPage(1, PAGE_SIZE);
            }
            session.setAttribute("info", pageInfo);
            return "删除操作成功";
        } else {
            return "批量删除失败！";
        }
    }
}