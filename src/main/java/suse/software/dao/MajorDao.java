package suse.software.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 专业数据表 major
 */
@Repository
public interface MajorDao {
    public List<String> getMajorNamesByCollegeName(String collegeName);
    public Integer getMajorIdByMajorName(String majorName);
}
