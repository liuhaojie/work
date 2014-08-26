package cn.maven.medol;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ServerDao {

	private SqlSession session;
	private SqlSessionFactory sessionFactory;
	
	public ServerDao(){
		if(sessionFactory == null){
			try {
				sessionFactory = new SqlSessionFactoryBuilder().build(Resources
						.getResourceAsReader("sqlMapConfig.xml"));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public List<ServerInfo> queryServersByGameId(int gameID) {
		// TODO Auto-generated method stub
		String stmtId = "ServerInfo.queryServersByGameId";
		session = sessionFactory.openSession();
		List<ServerInfo> serverInfos = session.selectList(stmtId, gameID);
		session.close();
		return serverInfos;
	}
	
	public int update(int id, int gameID, String gameName) {
		// TODO Auto-generated method stub
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setId(id);
		serverInfo.setGameID(gameID);
		serverInfo.setGameName(gameName);
		serverInfos.add(serverInfo);
		
		String stmtId = "ServerInfo.updateServers";
		session = sessionFactory.openSession();
		int row = session.insert(stmtId, serverInfos);
		return row;
	}

	

}
