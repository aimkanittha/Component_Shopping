/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import javax.ejb.Local;

/**
 *
 * @author wachirapong
 */
@Local
public interface MemberTableLocal {

    int getCount();

    Boolean getMemberByUsername(String Username);
    
}
