package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipesquisas.model.entity.Candidato;
import unipesquisas.model.entity.Escolaridade;

public class CandidatoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1873619323070110142L; 

	public void salvar (Candidato candidato) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO candidato (nome, " +
						"datanascimento, telefoneres, telefonecel, email, password, " +
						"idescolaridade, sexo, dddres, dddcel) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "idcandidato" });
				stmt.setString(1, candidato.getNome());
				stmt.setDate(2, convertDate(candidato.getDatanascimento()));
				stmt.setString(3, candidato.getTelefoneres().replace("(", "").replace(")", "").replace(" ", "").replace("-", ""));
				stmt.setString(4, candidato.getTelefonecel().replace("(", "").replace(")", "").replace(" ", "").replace("-", ""));
				stmt.setString(5, candidato.getEmail());
				stmt.setString(6, candidato.getPassword());
				stmt.setInt(7, candidato.getEscolaridade().getIdescolaridade());
				stmt.setString(8, candidato.getSexo());
				stmt.setString(9, candidato.getDddres());
				stmt.setString(10, candidato.getDddcel());

				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				candidato.setIdcandidato(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Candidato candidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE candidato SET nome = ?, " +
						"datanascimento = ?, telefoneres = ?, " +
						"telefonecel = ?, email = ?, password = ?, idescolaridade = ?, " +
						"sexo = ?, dddres = ?, dddcel = ? " +
						"WHERE idcandidato = ?");
				stmt.setString(1, candidato.getNome());
				stmt.setDate(2, convertDate(candidato.getDatanascimento()));
				stmt.setString(3, candidato.getTelefoneres().replace("(", "").replace(")", "").replace(" ", "").replace("-", ""));
				stmt.setString(4, candidato.getTelefonecel().replace("(", "").replace(")", "").replace(" ", "").replace("-", ""));
				stmt.setString(5, candidato.getEmail());
				stmt.setString(6, candidato.getPassword());
				stmt.setInt(7, candidato.getEscolaridade().getIdescolaridade());
				stmt.setString(8, candidato.getSexo());
				stmt.setString(9, candidato.getDddres());
				stmt.setString(10, candidato.getDddcel());
				stmt.setInt(11, candidato.getIdcandidato());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar candidato = " + candidato.getIdcandidato());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM candidato WHERE idcandidato = ?");
				stmt.setInt(1, idCandidato);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir candidato ID = " + idCandidato);
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

	public List<Candidato> listarCandidatos(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, c.telefoneres, " +
						"c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"WHERE ce.idempresa = ? " +
						"ORDER BY c.nome");
				stmt.setInt(1, idEmpresa);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Candidato candidato, ResultSet rs) throws DAOException {
		try {
			candidato.setIdcandidato(rs.getInt("idcandidato"));
			candidato.setNome(rs.getString("nome"));
			candidato.setDatanascimento(rs.getDate("datanascimento"));
			candidato.setTelefoneres(rs.getString("telefoneres"));
			candidato.setTelefonecel(rs.getString("telefonecel"));
			candidato.setEmail(rs.getString("email"));
			candidato.setPassword(rs.getString("password"));
			candidato.getEscolaridade().setIdescolaridade(rs.getInt("idescolaridade"));
			candidato.setSexo(rs.getString("sexo"));
			candidato.setDddres(rs.getString("dddres"));
			candidato.setDddcel(rs.getString("dddcel"));
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Candidato carregar(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idcandidato, nome, " +
						"datanascimento, telefoneres, telefonecel, email, password, " +
						"idescolaridade, sexo, dddres, dddcel " +
						"FROM candidato " +
						"WHERE idcandidato = ?");
				stmt.setInt(1, idCandidato);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Candidato candidato = new Candidato();
				candidato.setEscolaridade(new Escolaridade());
				preencher(candidato, rs);
				return candidato;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public boolean existeEmail(String email) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT email FROM candidato WHERE email = ?");
				stmt.setString(1, email);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
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

	public List<Candidato> listarCandidatosPorNome(String nome, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, " +
						"c.telefoneres, c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"WHERE c.nome like ? AND ce.idempresa = ? ORDER BY c.nome");
				stmt.setString(1, "%"+nome+"%");
				stmt.setInt(2, idEmpresa);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Candidato> listarCandidatosPorID(Integer idCandidato, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, c.telefoneres, " +
						"c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"WHERE c.idcandidato = ? AND ce.idempresa = ?");
				stmt.setInt(1, idCandidato);
				stmt.setInt(2, idEmpresa);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Candidato> listaCandidatosPorEscolaridade(Integer idEscolaridade, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, " +
						"c.telefoneres, c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"INNER JOIN escolaridade e ON e.idescolaridade=c.idescolaridade " +
						"WHERE ce.idempresa = ? AND c.idescolaridade = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idEscolaridade);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Candidato> listaCandidatosPorInstituicao(Integer idInstituicao,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, " +
						"c.telefoneres, c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"INNER JOIN candidatoinstituicao ci ON ci.idcandidato=c.idcandidato " +
						"WHERE ce.idempresa = ? AND ci.idinstituicao = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idInstituicao);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Candidato> listaCandidatosPorCurso(Integer idCurso,
			Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.idcandidato, c.nome, " +
						"c.datanascimento, " +
						"c.telefoneres, c.telefonecel, c.email, c.password, c.idescolaridade, " +
						"c.sexo, c.dddres, c.dddcel " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON c.idcandidato=ce.idcandidato " +
						"INNER JOIN candidatocurso cc ON cc.idcandidato=c.idcandidato " +
						"WHERE ce.idempresa = ? AND cc.idcurso = ?");
				stmt.setInt(1, idEmpresa);
				stmt.setInt(2, idCurso);
				
				List<Candidato> listaCandidatos = new ArrayList<Candidato>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Candidato candidato = new Candidato();
					candidato.setEscolaridade(new Escolaridade());
					preencher(candidato, rs);
					listaCandidatos.add(candidato);
				}

				return listaCandidatos;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean existeEmailEmpresa(String eMail, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.email FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"WHERE c.email = ? AND ce.idempresa = ?");
				stmt.setString(1, eMail);
				stmt.setInt(2, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
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

	public String getEmail(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.email " +
						"FROM candidato c " +
						"WHERE c.idcandidato = ? ");
				stmt.setInt(1, idCandidato);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getString("email");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public String getSenha(String email) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT c.password " +
						"FROM candidato c " +
						"WHERE c.email = ? ");
				stmt.setString(1, email);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getString("password");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Boolean criarNovaSenha(String emailAtual, String senha, String novaSenha) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE candidato SET password = ? " +
						"WHERE email = ? AND password = ?");
				stmt.setString(1, novaSenha);
				stmt.setString(2, emailAtual);
				stmt.setString(3, senha);

				if (stmt.executeUpdate() != 1) {
					return false;
//					throw new DAOException("Erro ao atualizar candidato");
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

	public Integer getTotalCandidatos(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(c.idcandidato) as total " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"WHERE ce.idempresa = ? ");
				stmt.setInt(1, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getInt("total");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Integer getTotalCandidatosCelular(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(c.idcandidato) as total " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"WHERE ce.idempresa = ? AND c.telefonecel <> ''");
				stmt.setInt(1, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getInt("total");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Integer getTotalCandidatosCelular() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(c.idcandidato) as total " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato " +
						"WHERE c.telefonecel <> ''");
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getInt("total");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Integer getTotalCandidatos() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT count(c.idcandidato) as total " +
						"FROM candidato c " +
						"INNER JOIN candidatoempresa ce ON ce.idcandidato=c.idcandidato");
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return rs.getInt("total");
				}

				return null;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean cancelarAssinatura(String emailAtual, String senhaAtual, Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE candidatoempresa ce " +
						"INNER JOIN candidato c ON c.idcandidato=ce.idcandidato " +
						"SET ce.receberemail = 0 " +
						"WHERE c.email = ? AND c.password = ? AND ce.idempresa = ?");
				stmt.setString(1, emailAtual);
				stmt.setString(2, senhaAtual);
				stmt.setInt(3, idEmpresa);

				if (stmt.executeUpdate() != 1) {
					return false;
//					throw new DAOException("Erro ao atualizar candidato");
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

}
