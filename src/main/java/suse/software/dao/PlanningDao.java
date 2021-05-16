package suse.software.dao;

import suse.software.views.PlanningView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningDao {
    public Boolean checkIfOpen(@Param("semesterId") Integer semesterId,@Param("majorId") Integer majorId,@Param("grade") Integer grade,@Param("cno") Integer cno);
    public Boolean changeIfOpen(@Param("semesterId") Integer semesterId,@Param("majorId") Integer majorId,@Param("grade") Integer grade,@Param("cno") Integer cno,

                                @Param("ifOpen") Boolean ifOpen);
}
