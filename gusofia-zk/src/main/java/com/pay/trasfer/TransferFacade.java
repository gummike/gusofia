package com.pay.trasfer;

/**
 * @author gulidong
 * @date 17/6/21
 */
public interface TransferFacade {

    /**
     * 转账
     *
     * @param request
     * @return  false&&response.status=-1  失败
     *          true&&response.status=1  成功
     *          其他情况全部认为未知异常
     */
    Result<Response> transfer(MerchantTransferRequest request);
}
