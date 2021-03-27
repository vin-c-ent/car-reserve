package com.car.control;

import com.car.entity.BrandEntity;
import com.car.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/index")
    public String getAllRatingList(HttpServletRequest request, Map<String, Object> paramMap) {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        paramMap.put("brandEntityList", brandEntityList);
        return "index";
    }
}
