package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Empresa;
import unipesquisas.model.entity.Instituicao;

public class InstituicaoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4603868894832139956L;
	
	public void salvar (Instituicao instituicao) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO instituicao (instituicao, endereco, bairro, cidade, estado, " +
						"cep, diretor, telefone, telefonecel, email, idempresa, sigla, dddtel, dddcel) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "idinstituicao" });
				stmt.setString(1, instituicao.getInstituicao());
				stmt.setString(2, instituicao.getEndereco());
				stmt.setString(3, instituicao.getBairro());
				stmt.setString(4, instituicao.getCidade());
				stmt.setString(5, instituicao.getEstado());
				stmt.setString(6, instituicao.getCep().replace(".", "").replace("-", ""));
				stmt.setString(7, instituicao.getDiretor());
				stmt.setString(8, instituicao.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(9, instituicao.getTelefonecel().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(10, instituicao.getEmail());
				stmt.setInt(11, instituicao.getEmpresa().getIdempresa());
				stmt.setString(12, instituicao.getSigla());
				stmt.setString(13, instituicao.getDddtel());
				stmt.setString(14, instituicao.getDddcel());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				instituicao.setIdinstituicao(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Instituicao instituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE instituicao SET instituicao = ?, endereco = ?, bairro = ?, " +
						"cidade = ?, estado = ?, cep = ?, diretor = ?, telefone = ?, telefonecel = ?, email = ?, " +
						"sigla = ?, dddtel = ?, dddcel = ? " +
						"WHERE idinstituicao = ?");
				stmt.setString(1, instituicao.getInstituicao());
				stmt.setString(2, instituicao.getEndereco());
				stmt.setString(3, instituicao.getBairro());
				stmt.setString(4, instituicao.getCidade());
				stmt.setString(5, instituicao.getEstado());
				stmt.setString(6, instituicao.getCep().replace(".", "").replace("-", ""));
				stmt.setString(7, instituicao.getDiretor());
				stmt.setString(8, instituicao.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(9, instituicao.getTelefonecel().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(10, instituicao.getEmail());
				stmt.setString(11, instituicao.getSigla());
				stmt.setString(12, instituicao.getDddtel());
				stmt.setString(13, instituicao.getDddcel());
				stmt.setInt(14, instituicao.getIdinstituicao());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar empresa = " + instituicao.getIdinstituicao());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM instituicao WHERE idinstituicao = ?");
				stmt.setInt(1, idInstituicao);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir instituicao ID = " + idInstituicao);
					return false;
				}
				
				return true;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarInstituicoes(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idinstituicao, instituicao, endereco, bairro, " +
						"cidade, estado, cep, diretor, telefone, telefonecel, email, idempresa, sigla, dddtel, dddcel " +
						"FROM instituicao " +
						"WHERE idempresa = ? ORDER BY instituicao");
				stmt.setInt(1, idEmpresa);
				
				List<Instituicao> listaInsituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao instituicao = new Instituicao();
					instituicao.setEmpresa(new Empresa());
					preencher(instituicao, rs);
					listaInsituicoes.add(instituicao);
				}

				return listaInsituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Instituicao instituicao, ResultSet rs) throws DAOException {
		try {
			instituicao.setIdinstituicao(rs.getInt("idinstituicao"));
			instituicao.setInstituicao(rs.getString("instituicao"));
			instituicao.setSigla(rs.getString("sigla"));
			instituicao.setEndereco(rs.getString("endereco"));
			instituicao.setBairro(rs.getString("bairro"));
			instituicao.setCidade(rs.getString("cidade"));
			instituicao.setEstado(rs.getString("estado"));
			instituicao.setCep(rs.getString("cep"));
			instituicao.setDiretor(rs.getString("diretor"));
			instituicao.setTelefone(rs.getString("telefone"));
			instituicao.setTelefonecel(rs.getString("telefonecel"));
			instituicao.setEmail(rs.getString("email"));
			instituicao.setDddtel(rs.getString("dddtel"));
			instituicao.setDddcel(rs.getString("dddcel"));
			instituicao.getEmpresa().setIdempresa(rs.getInt("idempresa"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Instituicao carregar(Integer idInstituicao) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idinstituicao, instituicao, endereco, bairro, cidade, " +
						"estado, cep, diretor, telefone, telefonecel, email, idempresa, sigla, dddtel, dddcel " +
						"FROM instituicao " +
						"WHERE idinstituicao = ?");
				stmt.setInt(1, idInstituicao);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Instituicao instituicao = new Instituicao();
				instituicao.setEmpresa(new Empresa());
				preencher(instituicao, rs);
				return instituicao;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarPesquisaPorInstituicao(
			String tituloInstituicao, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idinstituicao, instituicao, endereco, bairro, cidade, " +
						"estado, cep, diretor, telefone, telefonecel, email, idempresa, sigla, dddtel, dddcel " +
						"FROM instituicao " +
						"WHERE idempresa = ? AND instituicao like ? " +
						"ORDER BY instituicao");
				stmt.setInt(1, idEmpresa);
				stmt.setString(2, "%"+tituloInstituicao+"%");
				
				List<Instituicao> listaInsituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao instituicao = new Instituicao();
					instituicao.setEmpresa(new Empresa());
					preencher(instituicao, rs);
					listaInsituicoes.add(instituicao);
				}

				return listaInsituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarInstituicaoPorPesquisa(Integer idPesquisa,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT i.idinstituicao, i.instituicao, i.endereco, i.bairro, i.cidade, " +
						"i.estado, i.cep, i.diretor, i.telefone, i.telefonecel, i.email, i.idempresa, i.sigla, i.dddtel, " +
						"i.dddcel " +
						"FROM instituicao i " +
						"INNER JOIN pesquisainstituicao pi ON pi.idinstituicao=i.idinstituicao " +
						"INNER JOIN pesquisa p ON p.idpesquisa=pi.idpesquisa " +
						"WHERE p.idempresa = ? AND pi.idpesquisa = ? " +
						"ORDER BY instituicao");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idPesquisa);
				
				List<Instituicao> listaInsituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao instituicao = new Instituicao();
					instituicao.setEmpresa(new Empresa());
					preencher(instituicao, rs);
					listaInsituicoes.add(instituicao);
				}

				return listaInsituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarInstituicaoPorId(int idInstituicao,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idinstituicao, instituicao, endereco, bairro, " +
						"cidade, estado, cep, diretor, telefone, telefonecel, email, idempresa, sigla, dddtel, dddcel " +
						"FROM instituicao " +
						"WHERE idempresa = ? AND idinstituicao = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idInstituicao);
				
				List<Instituicao> listaInsituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao instituicao = new Instituicao();
					instituicao.setEmpresa(new Empresa());
					preencher(instituicao, rs);
					listaInsituicoes.add(instituicao);
				}

				return listaInsituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarInstituicoesDisponiveis(Integer idCandidato,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT i.idinstituicao, i.instituicao, i.endereco, i.bairro, " +
						"i.cidade, i.estado, i.cep, i.diretor, i.telefone, i.telefonecel, i.email, i.idempresa, " +
						"i.sigla, i.dddtel, i.dddcel " +
						"FROM instituicao i " +
						"WHERE i.idEmpresa = ? AND i.idinstituicao " +
						"NOT IN (SELECT ci.idinstituicao FROM candidatoinstituicao ci " +
						"INNER JOIN instituicao i ON i.idinstituicao=ci.idinstituicao " +
						"WHERE ci.idcandidato = ? AND i.idempresa = ?) " +
						"ORDER BY i.instituicao");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idCandidato);
				stmt.setInt(3, idEmpresa);
				
				List<Instituicao> listaInstituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao i = new Instituicao();
					i.setEmpresa(new Empresa());
					preencher(i, rs);
					listaInstituicoes.add(i);
				}

				return listaInstituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Instituicao> listarResumoInstituicoes(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT i.idinstituicao, i.instituicao, count(c.idcandidato) as total " +
						"FROM instituicao i " +
						"INNER JOIN candidatoinstituicao ci ON ci.idinstituicao=i.idinstituicao " +
						"INNER JOIN candidato c ON c.idcandidato=ci.idcandidato " +
						"WHERE i.idempresa = ? " +
						"GROUP BY i.instituicao " +
						"ORDER BY i.instituicao");
				stmt.setInt(1, idEmpresa);
				
				List<Instituicao> listaInstituicoes = new ArrayList<Instituicao>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Instituicao i = new Instituicao();
					i.setIdinstituicao(rs.getInt("idinstituicao"));
					i.setInstituicao(rs.getString("instituicao"));
					i.setTotal(rs.getInt("total"));
					listaInstituicoes.add(i);
				}
				return listaInstituicoes;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
