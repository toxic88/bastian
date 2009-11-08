package de.bastian.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.List;

public class Timetable extends BaseModel {

  public static List<Timetable> getTimetable() {
    List<Timetable> t = new ArrayList<Timetable>();
    t.add(new Timetable(1, "7:50 - 8:35",   "BWL", "BWL/SAE", "WI",      "BWL/SAE", "OS"));
    t.add(new Timetable(2, "8:35 - 9:20",   "BWL", "BWL/SAE", "WI",      "BWL/SAE", "OS"));
    t.add(new Timetable(3, "9:40 - 10:25",  "ITS", "BWL/SAE", "ITS",     "SAE",     "WI"));
    t.add(new Timetable(4, "10:25 - 11:10", "ITS", "BWL/SAE", "ITS",     "SAE",     "WI"));
    t.add(new Timetable(5, "11:20 - 12:05", "ITS", "D/GK",    "ITS",     "OS",      "SAE"));
    t.add(new Timetable(6, "12:05 - 12:50", "ITS", "D/GK",    "ITS",     "OS",      "SAE"));
    t.add(new Timetable(7, "13:30 - 14:15", "ITS", "D/GK",    "(Cisco)", "BWL",     null));
    t.add(new Timetable(8, "14:15 - 15:00", "ITS", "D/GK",    null,      "BWL",     null));
    return t;
  }

  public Timetable() {
  }

  public Timetable(Integer id, String time, String monday, String tuesday, String wednesday, String thursday, String friday) {
    this.setId(id);
    this.setTime(time);
    this.setMonday(monday);
    this.setTuesday(tuesday);
    this.setWednesday(wednesday);
    this.setThursday(thursday);
    this.setFriday(friday);
  }

  public int getId() {
    return (Integer) this.get("id");
  }

  public void setId(Integer id) {
    this.set("id", id);
  }

  public String getTime() {
    return (String) this.get("time");
  }

  public void setTime(String time) {
    this.set("time", time);
  }

  public String getMonday() {
    return (String) this.get("monday");
  }

  public void setMonday(String monday) {
    this.set("monday", monday);
  }

  public String getTuesday() {
    return (String) this.get("tuesday");
  }

  public void setTuesday(String tuesday) {
    this.set("tuesday", tuesday);
  }

  public String getWednesday() {
    return (String) this.get("wednesday");
  }

  public void setWednesday(String wednesday) {
    this.set("wednesday", wednesday);
  }

  public String getThursday() {
    return (String) this.get("thursday");
  }

  public void setThursday(String thursday) {
    this.set("thursday", thursday);
  }

  public String getFriday() {
    return (String) this.get("friday");
  }

  public void setFriday(String friday) {
    this.set("friday", friday);
  }

}
