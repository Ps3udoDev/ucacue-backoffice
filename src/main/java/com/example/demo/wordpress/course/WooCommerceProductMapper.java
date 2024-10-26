package com.example.demo.wordpress.course;

import com.example.demo.courses.Course;
import com.example.demo.courses.types.DescriptionItem;
import com.example.demo.courses.types.TabContent;
import com.example.demo.wordpress.course.types.CategoryIdDTO;
import com.example.demo.wordpress.common.ImageDTO;
import com.example.demo.wordpress.common.MetaDataDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WooCommerceProductMapper {
    public WooCommerceProductDTO toWooCommerceDTO(Course course) {

        WooCommerceProductDTO productDTO = new WooCommerceProductDTO();
        productDTO.setName(course.getName());
        productDTO.setSlug(course.getInternalCode());
        productDTO.setRegular_price(course.getPrice().toString());
        productDTO.setManage_stock(course.getManageStock());
        productDTO.setStock_quantity(course.getStockQuantity());

        List<CategoryIdDTO> categories = new ArrayList<>();
        categories.add(new CategoryIdDTO(course.getIdCategoryWooCommerce()));
        productDTO.setCategories(categories);

        List<ImageDTO> images = new ArrayList<>();
        if (course.getImage() != null && course.getImage().get("id") != null) {
            Integer imageId = (Integer) course.getImage().get("id");
            String imageUrl = course.getImage().get("src").toString();
            images.add(new ImageDTO(imageId, imageUrl));
        } else if (course.getImage() != null) {
            String imageUrl = course.getImage().get("src").toString();
            images.add(new ImageDTO(null, imageUrl));
        }

        productDTO.setImages(images);

        List<MetaDataDTO> metaData = new ArrayList<>();
        metaData.add(new MetaDataDTO("id_item_erp", String.valueOf(course.getIdErp())));
        metaData.add(new MetaDataDTO("lugar_del_curso", course.getCourseLocation()));
        metaData.add(new MetaDataDTO("fecha_del_curso", course.getStartDate() != null ? course.getStartDate().toString() : null));
        metaData.add(new MetaDataDTO("hora_del_curso", course.getStartTime() != null ? course.getStartTime().toString() : null));
        metaData.add(new MetaDataDTO("url_curso_moodle", course.getCourseUrlMoodle()));
        metaData.add(new MetaDataDTO("id_curso_moodle", String.valueOf(course.getIdCourseMoodle())));

        String shortDescriptionHtml = convertShortDescriptionToHtml(course.getShortDescription());
        productDTO.setShort_description(shortDescriptionHtml);

        String longDescriptionHtml = convertLongDescriptionToHtml(course.getLongDescription());
        metaData.add(new MetaDataDTO("_et_pb_old_content", longDescriptionHtml));

        productDTO.setMeta_data(metaData);

        if (course.getWithIVA() != null) {
            productDTO.setTax_status(course.getWithIVA() ? "taxable" : "none");
            productDTO.setTax_class(course.getWithIVA() ? "IVA" : null);
        }

        return productDTO;
    }

    private String convertShortDescriptionToHtml(List<DescriptionItem> shortDescription) {
        if (shortDescription == null || shortDescription.isEmpty()) {
            return "";
        }

        StringBuilder html = new StringBuilder("<table width=\"1209\"><thead><tr>");

        for (DescriptionItem item : shortDescription) {
            html.append(String.format("<td width=\"272\"><b>%s</b></td>", item.getTitle()));
        }

        html.append("</tr></thead><tbody><tr>");

        for (DescriptionItem item : shortDescription) {
            html.append(String.format("<td width=\"272\">%s</td>", item.getDescription().replace("<br/>", "\n")));
        }

        html.append("</tr></tbody></table>");
        return html.toString();
    }

    public String convertLongDescriptionToHtml(List<TabContent> tabContents) {
        StringBuilder html = new StringBuilder();
        html.append("[tabs slidertype=\"top tabs\"]\r\n");
        html.append("<ul class=\"et-tabs-control\" style=\"top: 0px\">\r\n");

        for (int i = 0; i < tabContents.size(); i++) {
            TabContent tab = tabContents.get(i);
            html.append("\t<li").append(i == 0 ? " class=\"active\"" : "").append("><a href=\"#\">").append(tab.getName()).append("</a></li>\r\n");
        }

        html.append("</ul>\r\n[tabcontent]\r\n");

        for (TabContent tab : tabContents) {
            html.append("[tab]\r\n");
            if ("editor".equals(tab.getType())) {
                html.append(tab.getContent().getContent()).append("\r\n");
            } else if ("pdf".equals(tab.getType())) {
                html.append("<object style=\"width: 100%; height: 90vh\" data=\"").append(tab.getContent().getSrc()).append("\" type=\"application/pdf\">Unable to display PDF file. <a href=\"").append(tab.getContent()).append("\">Download</a> instead.</object>\r\n");
            }
            html.append("[/tab]\r\n");
        }

        html.append("[/tabcontent]\r\n[/tabs]");
        return html.toString();
    }
}
