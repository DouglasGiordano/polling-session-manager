package br.com.softdesign.douglasgiordano.pollingsessionmanager.model;

/**
 * @author Douglas Montanha Giordano
 * Class to manage the path of each API resource.
 */
public class Path {
    public static final String SUPER_PATH = "/api/v1";
    public static final String AGENDA = SUPER_PATH + "/agenda";//pt-br = pauta
    public static final String CREATE_AGENDA = "/create"; //pt-br = criação agenda
    public static final String OPEN_VOTING_AGENDA = "/voting/open";
    public static final String VOTING_AGENDA = "/voting/vote"; //pt-br = votação agenda
    public static final String RESULT_VOTING_AGENDA = "/voting/result"; //pt-br = resultado votação
}
