package com.cpg.bim.service;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cpg.bim.entity.PermRole;
import com.cpg.bim.exception.PermroleAlreadyExistsException;
import com.cpg.bim.exception.PermroleNotFoundException;
import com.cpg.bim.repository.PermroleRepository;
 
@Service
public class PermroleService {
 
   
    @Autowired
    private PermroleRepository permroleRepository;
 
    // Add a permrole
    public void addPerm(PermRole perm) {
        Optional<PermRole> existingPerm = permroleRepository.findByRolenumber(perm.getRolenumber());
        if (existingPerm.isPresent()) {
            throw new PermroleAlreadyExistsException("PermRole with RoleNumber " + perm.getRolenumber() + " already exists.");
        }
        
        permroleRepository.save(perm);
    }
 
    // Get permrole by role number
    public Optional<PermRole> getPermrole(Integer rolenumber) {
        Optional<PermRole> permrole = permroleRepository.findByRolenumber(rolenumber);
        if (permrole.isEmpty()) {
            throw new PermroleNotFoundException("PermRole with RoleNumber " + rolenumber + " not found.");
        }
        return permrole;
    }
 
    // Update permrole by role number
    public PermRole updatePermrole(Integer rolenumber, String permRoleName) {
        Optional<PermRole> permroleOptional = permroleRepository.findByRolenumber(rolenumber);
        if (permroleOptional.isPresent()) {
            PermRole permrole = permroleOptional.get();
            permrole.setPermrole(permRoleName);
            return permroleRepository.save(permrole);
        } else {
            throw new PermroleNotFoundException("PermRole with RoleNumber " + rolenumber + " not found.");
        }
    }
 
}