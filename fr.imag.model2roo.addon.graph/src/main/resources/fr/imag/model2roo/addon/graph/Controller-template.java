// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package __TOP_PACKAGE__.web;

import __TOP_PACKAGE__.domain.__ENTITY__;
import __TOP_PACKAGE__.repository.__ENTITY__Repository;
import __TOP_PACKAGE__.web.__ENTITY__Controller;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect __ENTITY__Controller_Graph_Controller {

    @Autowired
    __ENTITY__Repository __ENTITY__Controller.__ENTITY_LOWER_CASE__Repository;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String __ENTITY__Controller.create(@Valid __ENTITY__ __ENTITY_LOWER_CASE__, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, __ENTITY_LOWER_CASE__);
            return "__ENTITY_PLURAL_LOWER_CASE__/create";
        }
        uiModel.asMap().clear();
        __ENTITY_LOWER_CASE__Repository.save(__ENTITY_LOWER_CASE__);
        return "redirect:/__ENTITY_PLURAL_LOWER_CASE__/" + encodeUrlPathSegment(__ENTITY_LOWER_CASE__.getNodeId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String __ENTITY__Controller.createForm(Model uiModel) {
        populateEditForm(uiModel, new __ENTITY__());
        return "__ENTITY_PLURAL_LOWER_CASE__/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String __ENTITY__Controller.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("__ENTITY_LOWER_CASE__", __ENTITY_LOWER_CASE__Repository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "__ENTITY_PLURAL_LOWER_CASE__/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String __ENTITY__Controller.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("__ENTITY_PLURAL_LOWER_CASE__", __ENTITY_LOWER_CASE__Repository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) __ENTITY_LOWER_CASE__Repository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("__ENTITY_PLURAL_LOWER_CASE__", __ENTITY_LOWER_CASE__Repository.findAll());
        }
        return "__ENTITY_PLURAL_LOWER_CASE__/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String __ENTITY__Controller.update(@Valid __ENTITY__ __ENTITY_LOWER_CASE__, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, __ENTITY_LOWER_CASE__);
            return "__ENTITY_LOWER_CASE__/update";
        }
        uiModel.asMap().clear();
        __ENTITY_LOWER_CASE__Repository.save(__ENTITY_LOWER_CASE__);
        return "redirect:/__ENTITY_PLURAL_LOWER_CASE__/" + encodeUrlPathSegment(__ENTITY_LOWER_CASE__.getNodeId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String __ENTITY__Controller.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, __ENTITY_LOWER_CASE__Repository.findOne(id));
        return "__ENTITY_PLURAL_LOWER_CASE__/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String __ENTITY__Controller.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        __ENTITY__ __ENTITY_LOWER_CASE__ = __ENTITY_LOWER_CASE__Repository.findOne(id);
        __ENTITY_LOWER_CASE__Repository.delete(__ENTITY_LOWER_CASE__);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/__ENTITY_PLURAL_LOWER_CASE__";
    }
}