package br.com.softdesign.douglasgiordano.pollingsessionmanager.model;

/**
 * @author Douglas Montanha Giordano
 * Class to manage the path of each API resource.
 */
public class Path {
    public static final String SUPER_PATH = "/api/v1";
    public static final String AGENDA = SUPER_PATH + "/agenda";//pt-br = pauta
    public static final String OPEN_VOTING_AGENDA = "/{idAgenda}/voting/open"; // abertura da sessão de votação
    public static final String VOTING_AGENDA = "/{idAgenda}/voting/vote"; //pt-br = votação pauta
    public static final String RESULT_VOTING_AGENDA = "/{idAgenda}/voting/result"; //pt-br = resultado votação
}
