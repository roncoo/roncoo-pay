package com.roncoo.pay.controller.trade;

import com.roncoo.pay.banklink.utils.weixin.UploadUtils;
import com.roncoo.pay.banklink.utils.weixin.WxCityNo;
import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.trade.entity.RpMicroSubmitRecord;
import com.roncoo.pay.trade.service.RpMicroSubmitRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * 小微商户进件
 * Created by Quanf
 * 2019/1/29
 */
@Controller
@RequestMapping("/trade/micro/submit/record")
public class MicroSubmitRecordController {

    @Autowired
    private RpMicroSubmitRecordService rpMicroSubmitRecordService;

    /**
     * 进件列表
     *
     * @param rpMicroSubmitRecord
     * @param pageParam
     * @param model
     * @return
     */
    @RequiresPermissions("trade:micro:submit:record:list")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public void list(HttpServletRequest request, RpMicroSubmitRecord rpMicroSubmitRecord, PageParam pageParam, Model model) {
        PageBean pageBean = rpMicroSubmitRecordService.listPage(pageParam, rpMicroSubmitRecord);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpMicroSubmitRecord", rpMicroSubmitRecord);
    }

    /**
     * 添加跳转
     *
     * @param model
     * @return
     */
    @RequiresPermissions("trade:micro:submit:record:add")
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI(Model model) {
        model.addAttribute("wxCityNoList", WxCityNo.getList());
        return "trade/micro/submit/record/add";
    }

    /**
     * 添加数据
     *
     * @param model
     * @param rpMicroSubmitRecord
     * @param dwz
     * @return
     */
    @RequiresPermissions("trade:micro:submit:record:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, RpMicroSubmitRecord rpMicroSubmitRecord, DwzAjax dwz) {
        Map<String, Object> returnMap = rpMicroSubmitRecordService.microSubmit(rpMicroSubmitRecord);
        if ("SUCCESS".equals(returnMap.get("return_code"))) {
            if ("SUCCESS".equals(returnMap.get("result_code"))) {
                dwz.setStatusCode(DWZ.SUCCESS);
                dwz.setMessage(DWZ.SUCCESS_MSG);
                model.addAttribute("dwz", dwz);
                return DWZ.AJAX_DONE;
            } else {
                dwz.setStatusCode(DWZ.ERROR);
                dwz.setMessage(returnMap.get("err_code_des").toString());
                model.addAttribute("dwz", dwz);
                return DWZ.AJAX_DONE;
            }
        } else {
            dwz.setStatusCode(DWZ.ERROR);
            dwz.setMessage(returnMap.get("return_msg").toString());
            model.addAttribute("dwz", dwz);
            return DWZ.AJAX_DONE;
        }
    }

    @RequiresPermissions("trade:micro:submit:record:add")
    @ResponseBody
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestBody MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        Map<String, Object> storeEntrancePicMap = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            final File excelFile = File.createTempFile(StringUtil.get32UUID(), prefix);
            // MultipartFile to File
            file.transferTo(excelFile);
            storeEntrancePicMap = UploadUtils.upload(excelFile);
            UploadUtils.deleteFile(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storeEntrancePicMap;
    }

    @RequiresPermissions("trade:micro:submit:record:query")
    @RequestMapping(value = "/query/{businessCode}")
    public String checkNotify(ModelMap model, @PathVariable(name = "businessCode") String businessCode) {
        Map<String, Object> returnMap = rpMicroSubmitRecordService.microQuery(businessCode);
        model.addAttribute("sign_url", returnMap.get("sign_url"));
        model.addAttribute("returnMap", returnMap);
        return "trade/micro/submit/record/query";
    }

}
