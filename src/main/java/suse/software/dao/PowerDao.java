package suse.software.dao;

import suse.software.domain.Power;
import org.springframework.stereotype.Repository;


@Repository
public interface PowerDao {
    public Power getPower(String powername);

    public void updatePower(Power power);

}
