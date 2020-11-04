package com.jungo.jungocrawling;

import com.jungo.jungocrawling.Account.ItemRank;
import com.jungo.jungocrawling.Account.ItemRankRepository;
import com.jungo.jungocrawling.Account.ItemRepository;
import com.jungo.jungocrawling.Account.Item;
import com.jungo.jungocrawling.utils.Criteria;
import com.jungo.jungocrawling.utils.PageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
public class CrawlingController {

    DecimalFormat decimalFormat = new DecimalFormat("###,###");

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemRankRepository itemRankRepository;


    @RequestMapping(value = "/")
    public String home(Model model){
        List<Item> list1 = itemRepository.findByHomeone();
        List<Item> list2 = itemRepository.findByHometwo();
        for (int i = 0; i < list1.size(); i++){
            list1.get(i).setPrice_html(decimalFormat.format(list1.get(i).getPrice()));
        }
        for (int i = 0; i < list2.size(); i++){
            list2.get(i).setPrice_html(decimalFormat.format(list2.get(i).getPrice()));
        }
        model.addAttribute("list1",list1);
        model.addAttribute("list2",list2);
        model.addAttribute("login", true);
        return "index";
    }

    @RequestMapping(value = "/as", method = RequestMethod.GET)
    public String jungocrawling(Model model, @RequestParam("keyword") String keyword, @ModelAttribute("cri") Criteria cri){
        int count = -1;
        //int buf = 0;
        //int count_buf = 0;
        //while (count != 0){
        //    count_buf = count;
        //    count = itemRepository.countByTitleContains(keyword, PageRequest.of(buf,100));
       //     buf++;
       // }
      //  count = count_buf + ((buf - 1) * 100);
        count = itemRepository.countByTitleContains(keyword);

        if (count == 0)
            return "noItem";
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(count);
        System.out.println(cri.getPage());
        List<Item> item = itemRepository.findByTitleContainsOrderByIdDesc(keyword, PageRequest.of(cri.getPage() - 1, 12));
        for (int i = 0; i < item.size(); i++){
            item.get(i).setPrice_html(decimalFormat.format(item.get(i).getPrice()));
        }
        Optional<ItemRank> title = itemRankRepository.findByTitle(keyword);
        if (title.isEmpty()){
            ItemRank itemRank = new ItemRank();
            itemRank.setCount(0);
            itemRank.setTitle(keyword);
            itemRankRepository.save(itemRank);
        } else {
            title.get().setCount(title.get().getCount() + 1);
            itemRankRepository.save(title.get());
        }

        model.addAttribute("items", item);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("keyword", keyword);
        return "item";


    }
    @GetMapping("/as/asc")
    public String ASC(Model model, @RequestParam("keyword") String keyword){

        List<Item> item = itemRepository.findByASC(keyword);
        String[] address = new String[item.size()];
        String[] title = new String[item.size()];
        String[] img = new String[item.size()];
        int[] price = new int[item.size()];

        for(int i = 0; i< item.size(); i++) {
            address[i] = item.get(i).getAddress();
            img[i] = item.get(i).getImg();
            title[i] = item.get(i).getTitle();
            price[i] = item.get(i).getPrice();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("title", title);
        model.addAttribute("address", address);
        model.addAttribute("img", img);
        model.addAttribute("price", price);

        return "crawlingOk";
    }
    @GetMapping("/as/desc")
    public String DESC(Model model, @RequestParam("keyword") String keyword){

        List<Item> item = itemRepository.findByDESC(keyword);
        String[] address = new String[item.size()];
        String[] title = new String[item.size()];
        String[] img = new String[item.size()];
        int[] price = new int[item.size()];

        for(int i = 0; i< item.size(); i++) {
            address[i] = item.get(i).getAddress();
            System.out.println(address[i]);
            img[i] = item.get(i).getImg();
            System.out.println(img[i]);
            title[i] = item.get(i).getTitle();
            System.out.println(title[i]);
            price[i] = item.get(i).getPrice();
            System.out.println(price[i]);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("title", title);
        model.addAttribute("address", address);
        model.addAttribute("img", img);
        model.addAttribute("price", price);

        return "crawlingOk";
    }


}
