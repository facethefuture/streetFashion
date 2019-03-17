package com.fashion.www.dao.hotRecommend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fashion.www.goods.Goods;
@Repository
public class HotRecommendDao {
	@Autowired
	private DataSource dataSource;
	public List<Goods> queryHotRecommends(int currentPage,int perPage){
		String querySql = "SELECT id,title,coverImage,description FROM goods_recommend ORDER BY id DESC LIMIT ?,?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Goods> goods = new ArrayList<Goods>();
		try{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(querySql);
			stmt.setInt(1, (currentPage - 1) * perPage);
			stmt.setInt(2, currentPage * perPage);
			rs = stmt.executeQuery();
			while(rs.next()){
				goods.add(new Goods(rs.getInt("id"),rs.getString("title"),rs.getString("coverImage"),rs.getString("description")));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if (stmt != null){
						stmt.close();
					}
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					try{
						if (conn != null) {
							conn.close();
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		}
		return goods;
	}
	public int getTotalCount(){
		String querySql = "SELECT COUNT(id) AS count FROM goods_recommend ";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(querySql);
			rs = stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt("count");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if(stmt != null){
						stmt.close();
					}
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					try{
						if (conn != null){
							conn.close();
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(count);
		return count;
	}
}