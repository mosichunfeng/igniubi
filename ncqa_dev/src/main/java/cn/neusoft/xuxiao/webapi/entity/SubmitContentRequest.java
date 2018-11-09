package cn.neusoft.xuxiao.webapi.entity;

import java.util.Map;

public class SubmitContentRequest
{
  private int user_id;
  private int question_base_id;
  private Map<Integer, String> map;
  private String end_time;

  public int getUser_id()
  {
    return this.user_id;
  }

  public int getQuestion_base_id() {
    return this.question_base_id;
  }

  public Map<Integer, String> getMap() {
    return this.map;
  }

  public String getEnd_time() {
    return this.end_time;
  }

  public void setUser_id(int user_id)
  {
    this.user_id = user_id; } 
  public void setQuestion_base_id(int question_base_id) { this.question_base_id = question_base_id; } 
  public void setMap(Map<Integer, String> map) { this.map = map; } 
  public void setEnd_time(String end_time) { this.end_time = end_time; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof SubmitContentRequest)) return false; SubmitContentRequest other = (SubmitContentRequest)o; if (!other.canEqual(this)) return false; if (getUser_id() != other.getUser_id()) return false; if (getQuestion_base_id() != other.getQuestion_base_id()) return false; Object this$map = getMap(); Object other$map = other.getMap(); if (this$map == null ? other$map != null : !this$map.equals(other$map)) return false; Object this$end_time = getEnd_time(); Object other$end_time = other.getEnd_time(); return this$end_time == null ? other$end_time == null : this$end_time.equals(other$end_time); } 
  protected boolean canEqual(Object other) { return other instanceof SubmitContentRequest; } 
  public int hashCode() { int PRIME = 59; int result = 1; result = result * 59 + getUser_id(); result = result * 59 + getQuestion_base_id(); Object $map = getMap(); result = result * 59 + ($map == null ? 43 : $map.hashCode()); Object $end_time = getEnd_time(); return result * 59 + ($end_time == null ? 43 : $end_time.hashCode()); } 
  public String toString() { return "SubmitContentRequest(user_id=" + getUser_id() + ", question_base_id=" + getQuestion_base_id() + ", map=" + getMap() + ", end_time=" + getEnd_time() + ")";
  }
}