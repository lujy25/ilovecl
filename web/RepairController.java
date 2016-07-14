package com.ilovecl.web;

import com.ilovecl.entity.Repair;
import com.ilovecl.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 报修单的web层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 *         copyRight:The MIT License (MIT)
 *         Copyright (c) 2016 邱永臣
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *         the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 *         to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         <p>
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 *         Software.
 *         <p>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *         WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *         OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *         OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @since 2016-05-31 13:58
 */
@Controller
@RequestMapping("/repair")
public class RepairController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RepairService repairService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Repair> list = repairService.getRepqirByStudentId(1);

        model.addAttribute("list", list);

        return "list";
    }

    @RequestMapping(value = "/{repairId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("rapairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);

        model.addAttribute("repair", repair);

        return "detail";
    }
}
