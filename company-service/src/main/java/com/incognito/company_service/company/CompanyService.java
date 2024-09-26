package com.incognito.company_service.company;


import com.incognito.company_service.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    void updateCompany(Company company, Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);
    boolean deleteCompanyById(Long id);

}
