package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.models.Club;
import com.example.web.models.Comment;
import com.example.web.models.Coupon;
import com.example.web.models.security.RoleEntity;
import com.example.web.models.security.UserEntity;
import com.example.web.security.SecurityUtil;
import com.example.web.service.ClubService;
import com.example.web.service.CommentService;
import com.example.web.service.CouponService;
import com.example.web.service.security.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
private ClubService clubService; private UserService userService; private CommentService commentService; private CouponService couponService;
    @Autowired
    public ClubController(ClubService clubService , UserService userService, CommentService commentService, CouponService couponService) {
        this.clubService = clubService;this.userService=userService; this.commentService= commentService; this.couponService=couponService;
    }
    //Welcome page
    @GetMapping("/home")
    public String welcomePage()
    {
        return "homePage";
    }
    //Read
    @GetMapping("/clubs")
    public String listClubs(Model model,
                            @RequestParam(value="pageNo", defaultValue="0",required=false) int pageNo,
                            @RequestParam(value="pageSize", defaultValue="6",required=false) int pageSize)

    {
        UserEntity user = new UserEntity();
        ClubsPagination clubs = clubService.findAllClub(pageNo,pageSize);// метод из service
        String username = SecurityUtil.getSessionUser();
        if(username !=null)
        {
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);// if user entity will be null (пользователь не авторизован или не существует в системе.)
        model.addAttribute("clubs",clubs);
        return "clubs-list";// View - HTML file
    }
    //Detail page from list-detail pattern
    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model)
    {
        UserEntity user = new UserEntity();
        RoleEntity role = new RoleEntity();
        List<Comment> comments = commentService.getComments(clubId);
        List<Coupon> coupons = couponService.getCoupons(clubId);
        ClubDto clubDto = clubService.findClubById(clubId);
        String username = SecurityUtil.getSessionUser();
        if(username !=null)
        {
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);         // if user entity will be null (пользователь не авторизован или не существует в системе.)
        model.addAttribute("club",clubDto);      // доставить данные на страницу
        model.addAttribute("comments",comments); // get comments to page
        model.addAttribute("coupons",coupons);   // get coupons to page
        model.addAttribute("role",role);

        return "clubs-detail";
        // отображения подробной информации о клубе на основе его идентификатора (clubId)
        // , который вы получаете из пути запроса. Затем вы используете этот clubId для
        // получения соответствующих данных о клубе с помощью метода findClubById вашего сервиса.
        // Полученные данные сохраняются в объекте ClubDto и передаются в модель для отображения на странице clubs-detail.
    }
    //Create
    @GetMapping("/clubs/new")
    public String createClubForm(Model model)
    {
      Club club = new Club();
      model.addAttribute("club",club);
      return "clubs-create";
    }
    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto,
                           BindingResult result, Model model )
    {
        if(result.hasErrors())//hasErrors проверяет есть
            // ли ошибки к обьекте BindingResources
    {
        model.addAttribute("club",clubDto);
        return "clubs-create";
    }
      clubService.saveClub(clubDto);
      return "redirect:/clubs";
    }
    //Update
    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model)
    {
      ClubDto club = clubService.findClubById(clubId);
      model.addAttribute("club",club);
      return "clubs-edit";
    }
    @PostMapping("/clubs/{clubId}/edit")
    public String updateClubForm(@PathVariable("clubId") Long clubId ,
                                 @Valid @ModelAttribute("club") ClubDto clubDto ,
                                 BindingResult result,
                                 Model model) {
       if(result.hasErrors())//hasErrors проверяет есть ли ошибки к обьекте BindingResources
       {
           model.addAttribute("club",clubDto); //если без этой строки , то пользователь не увидит то что он ввел в данную строку где произошла ошибка
           return "clubs-edit";
       }
       clubDto.setId(clubId);
       clubService.updateClub(clubDto);
       return "redirect:/clubs"; // когда этот метод выполнется
                                 // --> нас вернет обратно на главную страницу
    }
    //Delete
    @GetMapping("/clubs/{clubId}/delete")
    public  String deleteClub(@PathVariable("clubId") Long clubId)
    {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }
    //Search
    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query , Model model)
    {
      List<ClubDto> clubs= clubService.searchClubs(query);
      model.addAttribute("clubs",clubs);
      return "clubs-list";
    }
}
