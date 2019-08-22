package org.ssm.dufy.dao;

import java.util.List;
import java.util.Map;

public interface BbsDao {
    
    public void addBBSContent(Map paramMap);    
    
    /**
     * 根据条件查询bbs
     * @param paramMap
     * @return
     */
    public List queryContentByParam(Map paramMap);
    
    public List queryCommentByParam(Map paramMap);
    
    public List queryPagingContentByParam(Map paramMap);
    
    public List queryNewPagingContentByParam(Map paramMap);
    
    public void removeBbsContent(Map paramMap);
    
    public Integer queryBbsCount(Map paramMap);
    
    public Integer queryNewBbsCount(Map paramMap);
    
    public Integer queryOwnBbsCount(Map paramMap);
    
    public void updateBbbsByParam(Map paramMap);
    
    
}
