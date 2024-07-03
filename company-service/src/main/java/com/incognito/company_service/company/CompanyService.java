package com.incognito.company_service.company;


import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    void updateCompany(Company company, Long id);
    boolean deleteCompanyById(Long id);

}
