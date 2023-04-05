package com.a05.simaya.event.controller;


import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.RoleEnum;
import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.model.ProgresModel;
import com.a05.simaya.event.payload.CreateEventDTO;
import com.a05.simaya.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private AnggotaService anggotaService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/tambah-event")
    public String getPage(Model model) {
        CreateEventDTO event = new CreateEventDTO();

        // Hanya menampilkan anggota dengan role SDM
        List<AnggotaModel> listAnggotaSDM = anggotaService.getListAnggotaBasedonRole(RoleEnum.SDM_OPERASIONAL);

        model.addAttribute("listAnggota", listAnggotaSDM);
        model.addAttribute("event", event);
        return "event/form-tambah-event";
    }

    @PostMapping(value = "/tambah-event")
    public String postForm(CreateEventDTO event, RedirectAttributes redirectAttributes) {
        if (eventService.tambahEvent(event)){
            redirectAttributes.addFlashAttribute("success", String.format("Event bernama %s berhasil ditambahkan!", event.getNamaEvent()));
        } else {
            redirectAttributes.addFlashAttribute("error", String.format("Event bernama %s sudah pernah ditambahkan!", event.getNamaEvent()));
        }
        return "redirect:/event/viewall";
    }

    @GetMapping(value = "/event/viewall")
    public String viewAllEvent(){
        return "event/daftar-event";
    }

    @GetMapping(value = "/event")
    public String getDashboardEvent(Model model) {
        List<EventModel> listOngoing = eventService.getListOngoing();
        List<EventModel> listUpcoming = eventService.getListUpcoming();
        model.addAttribute("listOngoing", listOngoing);
        model.addAttribute("listUpcoming", listUpcoming);
        return "event/dashboard-event";
    }

    @GetMapping(value = "/event/hapus/{id}")
    public String deleteEvent(
            @PathVariable(value = "id") Long idEvent,
            RedirectAttributes redirectAttributes
    ){
        Boolean result = eventService.deleteEvent(idEvent);
        EventModel event = eventService.getEventById(idEvent);

        if (result){
            redirectAttributes.addFlashAttribute("success", String.format("Event bernama %s berhasil dihapus!", event.getNamaEvent()));
            return "redirect:/event/viewall";
        } else {
            redirectAttributes.addFlashAttribute("error", "Event ini gagal dihapus! karena event ini memiliki progres");
            return "redirect:/event/{id}";
        }
    }

    @GetMapping(value="/event/{id}")
    public String viewDetailEvent(
            @PathVariable(value = "id") Long idEvent,
            Model model
    ){
        EventModel event = eventService.getEventById(idEvent);
        List<ProgresModel> listProgres = event.getListProgres();
        model.addAttribute("event", event);
        model.addAttribute("listProgres", listProgres);
        return "event/detail-event";
    }

}
