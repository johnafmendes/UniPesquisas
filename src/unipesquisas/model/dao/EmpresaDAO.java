package unipesquisas.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import unipesquisas.diversos.Diversos;
import unipesquisas.model.entity.Empresa;
import org.primefaces.model.UploadedFile;

public class EmpresaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4603868894832139956L;
	
	@Inject
	private Diversos diversos;
	
	public void upload (UploadedFile uploadedFile, String id){
		if(uploadedFile != null){
			String relativeWebPath = "/resources/logos/";
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
			
			//String fileName = diversos.copyFileToDir(uploadedFile, absoluteDiskPath, id);
			diversos.copyFileToDir(uploadedFile, absoluteDiskPath, id);
			
			//return fileName;
		}
//		String name = uploadedFile.getFileName();
//		long size = uploadedFile.getSize();
		//return null;
	}
	
	public void salvar (Empresa empresa) throws DAOException{
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("INSERT INTO empresa (nome, endereco, bairro, cidade, estado, cep, site, " +
						"email, telefone, responsavel, logomarca, status, ddd) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "idempresa" });
				stmt.setString(1, empresa.getNome());
				stmt.setString(2, empresa.getEndereco());
				stmt.setString(3, empresa.getBairro());
				stmt.setString(4, empresa.getCidade());
				stmt.setString(5, empresa.getEstado());
				stmt.setString(6, empresa.getCep().replace(".", "").replace("-", ""));
				stmt.setString(7, empresa.getSite());
				stmt.setString(8, empresa.getEmail());
				stmt.setString(9, empresa.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(10, empresa.getResponsavel());
				stmt.setString(11, empresa.getLogomarca());
				stmt.setInt(12, empresa.getStatus());
				stmt.setString(13, empresa.getDdd());
				
				stmt.executeUpdate();

				// Obtém o ID gerado pelo banco de dados e atribui ao objeto
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				empresa.setIdempresa(rs.getInt(1));

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizar(Empresa empresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE empresa SET nome = ?, endereco = ?, bairro = ?, cidade = ?, " +
						"estado = ?, cep = ?, site = ?, email = ?, telefone = ?, responsavel = ?, logomarca = ?, " +
						"status = ?, ddd = ? " +
						"WHERE idempresa = ?");
				stmt.setString(1, empresa.getNome());
				stmt.setString(2, empresa.getEndereco());
				stmt.setString(3, empresa.getBairro());
				stmt.setString(4, empresa.getCidade());
				stmt.setString(5, empresa.getEstado());
				stmt.setString(6, empresa.getCep().replace(".", "").replace("-", ""));
				stmt.setString(7, empresa.getSite());
				stmt.setString(8, empresa.getEmail());
				stmt.setString(9, empresa.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
				stmt.setString(10, empresa.getResponsavel());
				stmt.setString(11, empresa.getLogomarca());
				stmt.setInt(12, empresa.getStatus());
				stmt.setString(13, empresa.getDdd());
				stmt.setInt(14, empresa.getIdempresa());

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar empresa = " + empresa.getIdempresa());
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public boolean excluir(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("DELETE FROM empresa WHERE idempresa = ?");
				stmt.setInt(1, idEmpresa);

				if (stmt.executeUpdate() != 1) {
//					throw new DAOException("Erro ao excluir empresa ID = " + idEmpresa);
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

	public List<Empresa> listarEmpresas() throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idempresa, nome, endereco, bairro, cidade, estado, cep, site, " +
						"email, telefone, responsavel, logomarca, status, ddd " +
						"FROM empresa " +
						"ORDER BY nome");

				List<Empresa> listaEmpresas = new ArrayList<Empresa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Empresa empresa = new Empresa();
					preencher(empresa, rs);
					listaEmpresas.add(empresa);
				}

				return listaEmpresas;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private void preencher(Empresa empresa, ResultSet rs) throws DAOException {
		try {
			empresa.setIdempresa(rs.getInt("idempresa"));
			empresa.setNome(rs.getString("nome"));
			empresa.setEndereco(rs.getString("endereco"));
			empresa.setBairro(rs.getString("bairro"));
			empresa.setCidade(rs.getString("cidade"));
			empresa.setEstado(rs.getString("estado"));
			empresa.setCep(rs.getString("cep"));
			empresa.setSite(rs.getString("site"));
			empresa.setEmail(rs.getString("email"));
			empresa.setTelefone(rs.getString("telefone"));
			empresa.setResponsavel(rs.getString("responsavel"));
			empresa.setLogomarca(rs.getString("logomarca"));
			empresa.setStatus(rs.getInt("status"));
			empresa.setDdd(rs.getString("ddd"));

		} catch (SQLException e) {
			throw new DAOException(e);
		}	
	}

	public Empresa carregar(Integer idEmpresa) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT idempresa, nome, endereco, bairro, cidade, estado, cep, site, " +
						"email, telefone, responsavel, logomarca, status, ddd " +
						"FROM empresa " +
						"WHERE idempresa = ?");
				stmt.setInt(1, idEmpresa);
				
				ResultSet rs = stmt.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				Empresa empresa = new Empresa();
				preencher(empresa, rs);
				return empresa;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void atualizarLogomarca(String id, String filename) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("UPDATE empresa SET logomarca = ? WHERE idempresa = ?");
				stmt.setString(1, filename);
				stmt.setInt(2, Integer.parseInt(id));

				if (stmt.executeUpdate() != 1) {
					throw new DAOException("Erro ao atualizar empresa = " + id);
				}
			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}		
	}

	public List<Empresa> listarEmpresasPorCandidato(Integer idCandidato) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			try {
				conn = getConnection();
				stmt = conn.prepareStatement("SELECT e.idempresa, e.nome, e.endereco, e.bairro, e.cidade, e.estado, " +
						"e.cep, e.site, e.email, e.telefone, e.responsavel, e.logomarca, e.status, e.ddd " +
						"FROM empresa e " +
						"INNER JOIN candidatoempresa ON e.idempresa=candidatoempresa.idempresa " +
						"INNER JOIN candidato ON candidato.idcandidato=candidatoempresa.idcandidato " +
						"WHERE candidatoempresa.idcandidato = ? " +
						"ORDER BY e.nome");
				stmt.setInt(1, idCandidato);
				
				List<Empresa> listaEmpresas = new ArrayList<Empresa>();

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Empresa empresa = new Empresa();
					preencher(empresa, rs);
					listaEmpresas.add(empresa);
				}

				return listaEmpresas;

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
				stmt = conn.prepareStatement("SELECT email " +
						"FROM empresa " +
						"WHERE email = ?");
				stmt.setString(1, email);
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					return true;
				}

				return false;

			} finally {
				closeStatement(stmt);
				closeConnection();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
