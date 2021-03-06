package com.shw.test.account.presist.service.impl;

import com.shw.test.account.presist.exception.AccountPersistException;
import com.shw.test.account.presist.model.Account;
import com.shw.test.account.presist.service.AccountPersistService;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import sun.misc.IOUtils;

import java.io.*;
import java.util.List;

public class AccountPersistServiceImpl implements AccountPersistService {

    public static final String ELEMENT_ROOT = "account-persist";

    public static final String ELEMENT_ACCOUNTS = "accounts";

    public static final String ELEMENT_ACCOUNT_ID = "id";

    public static final String ELEMENT_ACCOUNT_NAME = "name";

    public static final String ELEMENT_ACCOUNT_PASSWORD = "password";

    public static final String ELEMENT_ACCOUNT_EMAIL = "email";

    public static final String ELEMENT_ACCOUNT_ACVITAVED = "activated";

    private String file;

    private SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException {

        File dataFile = new File(file);

        if(!dataFile.exists()){
              dataFile.getParentFile().mkdirs();

              Document doc = DocumentFactory.getInstance().createDocument();

              Element rootEle = doc.addElement(ELEMENT_ROOT);

              rootEle.addElement(ELEMENT_ACCOUNTS);

              writeDocument(doc);
        }
        try{
            return reader.read(new File(file));
        }catch (DocumentException e){
            throw new AccountPersistException("Unable to read persist data xml", e);
        }
    }

    private void writeDocument(Document document) throws AccountPersistException{
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(document);
        }catch (IOException e){
            throw new AccountPersistException("Unable to write persist data xml",e);
        }finally{
            try {
                if (out != null){
                    out.close();
                }
            }catch (IOException e){
                throw new AccountPersistException("Unable to write persist data xml",e);
            }
        }

    }

    

    public Account createAccount(Account account) throws AccountPersistException {

        Document document = readDocument();
        Element root = document.getRootElement().element(ELEMENT_ACCOUNTS);
        Element act = root.addElement("account");
        act.addAttribute("number", "1003");
        // 分别为stuEle添加名为name、age、sex的子元素，并为子元素设置文本内容
        act.addElement(ELEMENT_ACCOUNT_ID).setText(account.getId());
        act.addElement(ELEMENT_ACCOUNT_NAME).setText(account.getName());
        act.addElement(ELEMENT_ACCOUNT_EMAIL).setText(account.getEmail());
        act.addElement(ELEMENT_ACCOUNT_PASSWORD).setText(account.getPassword());
        act.addElement(ELEMENT_ACCOUNT_ACVITAVED).setText(String.valueOf(account.isActivated()));
        writeDocument(document);
        return account;
    }


    public Account readAccount(String id) throws AccountPersistException {
        Document document = readDocument();
        Element accountsEle = document.getRootElement().element(ELEMENT_ACCOUNTS);
        for (Element accountEle : (List<Element>)accountsEle.elements()){
            if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals("1")){
                return buildAccount(accountEle);
            }
        }
        return null;
    }

    private Account buildAccount(Element element){
        Account account = new Account();
        account.setId(element.elementText(ELEMENT_ACCOUNT_ID));
        account.setName(element.elementText(ELEMENT_ACCOUNT_NAME));
        account.setEmail(element.elementText(ELEMENT_ACCOUNT_EMAIL));
        account.setPassword(element.elementText(ELEMENT_ACCOUNT_PASSWORD));
        account.setActivated(("true".equals(element.elementText(ELEMENT_ACCOUNT_ACVITAVED))?true:false));

        return account;
    }

    public Account updateAccount(Account account) throws AccountPersistException {
        return null;
    }

    public void deleteAccount(String id) throws AccountPersistException {

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
