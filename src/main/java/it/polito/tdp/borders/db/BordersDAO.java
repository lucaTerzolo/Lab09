package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				Country country=new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				idMap.put(country.getCCode(), country);
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql="Select distinct state1no, state2no "
					+ "From contiguity "
					+ "where year<=? and conttype=1 ";
		Connection conn = ConnectDB.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			List<Border> result=new ArrayList<>();
			while (rs.next()) {
				result.add(new Border(rs.getInt("state1no"),rs.getInt("state2no")));
			}

			st.close();
			conn.close();
			return result;	

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore", e);
		}
	}
}
