package cn.neusoft.xuxiao.exception;

public class BusinessException extends CommonRuntimeException
{
  private static final long serialVersionUID = 3852715919578829470L;

  public BusinessException(String code, String msg)
  {
    super(code, msg);
  }
}