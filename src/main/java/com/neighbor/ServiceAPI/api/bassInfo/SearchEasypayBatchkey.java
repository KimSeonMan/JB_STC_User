package com.neighbor.ServiceAPI.api.bassInfo;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SearchEasypayBatchkey extends AbsQuery<Map> {

    private static final Log logger = LogFactory.getLog(SearchEasypayBatchkey.class);

    @Resource(name="bassInfoService")
    private BassInfoService bassInfoService;

    @Override
    public String getApiId() {
        // TODO Auto-generated method stub
        return "1113";
    }

    @Override
    public HttpMethod getHttpMethod() {
        // TODO Auto-generated method stub
        return HttpMethod.POST;
    }

    @Override
    public Class getMustParameter() throws AbsException {
        // TODO Auto-generated method stub
        return SearchEasypayBatchkey.MustParam.class;
    }

    @Override
    public Class getOptionParameter() throws AbsException {
        // TODO Auto-generated method stub
        return SearchEasypayBatchkey.OptionParam.class;
    }

    @Override
    protected String getQueryStr() {
        return null;
    }

    enum MustParam
    {
        MBER_ID,CNTER_CD
    }

    enum OptionParam
    {
    }

    @Override
    public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
                          String trId) throws AbsException {
        // TODO Auto-generated method stub
        httpSession = req.getSession();

        Map resultData = new HashMap();

        try {
            logger.info("START Query - ApiID[" + this.getApiId() + "] ");

            Map mapParameter = getParameterMap(req);

            _checkNullParameterValue(mapParameter);

            Map keyInfo = bassInfoService.searchEasypayBatchKey(mapParameter);

            resultData.put("result",keyInfo);

            logger.info("END Query - TXID[" + getApiId() + "] ");
        }catch (AbsAPIException e) {
            logger.error(e);
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(e);
            throw new ApiException("Input Error");
        } catch (Exception e) {
            logger.error(e);
            throw new ApiException("Error");
        }
        return resultData;
    }
}
