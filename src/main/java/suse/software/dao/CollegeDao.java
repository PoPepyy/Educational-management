package suse.software.dao;

import suse.software.domain.College;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学院 college
 */


@Repository
public interface CollegeDao {

    public List<College> getColleges();

    public College getCollegeById(String id);

    public List<String> getCollegeNames();

    public String getColNameById(int id);
}

