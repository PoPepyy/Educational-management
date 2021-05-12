package suse.software.dao;

import suse.software.domain.Power;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerDao {
    public List<Power> getPower();

    public void deletePower();

    public void updatePower(Power power);

    public void insertPower(Power power);
}
