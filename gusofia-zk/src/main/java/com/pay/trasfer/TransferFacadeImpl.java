package com.pay.trasfer;

/**
 * @author zi.you
 * @date 17/6/21
 */
public class TransferFacadeImpl implements TransferFacade {

    public Result<Response> transfer(MerchantTransferRequest request) {
        LOGGER.info("转账请求参数:" + request);
        Result<Response> result = new Result<Response>();
        try {
            Response response = new Response();
            response = merchantPaidService.doTransfer(request);
            result.setSuccess(true);
            result.setData(response);
        } catch (Exception e) {
            LOGGER.error("转账异常！", e);
            result.setSuccess(false);
            result.setErrorMsg("转账异常！");
            result.setExceptionMsg(e.getMessage());
        }
        LOGGER.info("转账处理返回:" + ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
        return result;
    }
}
