package com.mt.government.service;

import com.mt.government.model.Form;

import java.util.List;

public interface FormService {

    void Insert(Form form);

    List<Form> List();

    void Delete(Form form);

    Form GetById(Form form);
}
