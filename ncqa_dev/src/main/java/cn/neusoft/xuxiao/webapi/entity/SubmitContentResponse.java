package cn.neusoft.xuxiao.webapi.entity;

public class SubmitContentResponse
{
  public boolean equals(Object o)
  {
    if (o == this) return true; if (!(o instanceof SubmitContentResponse)) return false; SubmitContentResponse other = (SubmitContentResponse)o; return other.canEqual(this); } 
  protected boolean canEqual(Object other) { return other instanceof SubmitContentResponse; } 
  public int hashCode() { return 1; } 
  public String toString() { return "SubmitContentResponse()";
  }
}