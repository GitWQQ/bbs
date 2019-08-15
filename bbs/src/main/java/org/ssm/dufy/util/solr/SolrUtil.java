package org.ssm.dufy.util.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SolrUtil {
	//Solr服务器地址
	private static final String SOLR_URL="http://localhost:8082/solr";
	private static HttpSolrServer server=null;
	
	static{
		try{
			server=new HttpSolrServer(SOLR_URL);
			server.setAllowCompression(true);
			server.setConnectionTimeout(10000);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加索引
	 * @param page
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public static void addIndex(Page page) throws IOException, SolrServerException{
		server.addBean(page);
		//对索引进行优化
		server.optimize();
		server.commit();
	}
	
	/**
	 * 删除索引
	 */
	public static void delIndex(){
		try {
			server.deleteByQuery("*:*");
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<Page> search(String sKey,int start,int range,String sort,String field) throws SolrServerException{
		SolrQuery params=new SolrQuery();
		if(StringUtils.isNotBlank(sKey)){
			//高亮显示
			params.set("q","title:"+sKey);
			params.setHighlight(true);
			params.addHighlightField("title");
			params.setHighlightSimplePre("<font color=\"red\">");
			params.setHighlightSimplePost("</font>");
		}else{
			params.set("q","*:*");
		}
		//分页
		params.set("start",""+start);
		params.set("rows",""+range);
		
		//排序
		if(StringUtils.isNotBlank(sort)){
			if("asc".equals(sort)){
				params.setSort(field,SolrQuery.ORDER.asc);
			}else{
				params.setSort(field,SolrQuery.ORDER.desc);
			}
		}
		QueryResponse response=server.query(params);
		List<Page> results=response.getBeans(Page.class);
		if(StringUtils.isNotBlank(sKey)){
			Map<String,Map<String,List<String>>> map=response.getHighlighting();
			List<Page> list=new ArrayList<>();
			Page page=null;
			for(int i=0;i<results.size();i++){
				page=results.get(i);
				page.setTitle(map.get(page.getXh()).get("title").get(0));
				list.add(page);
			}
			return list;
		}else{
			return results;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(server);
		delIndex();
		
		/*List<Page> list=new ArrayList<>();
		try {
			list=SolrUtil.search("无问西",0,100,"","");
			for (Page page : list) {
				System.out.println(page.getTitle());
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
