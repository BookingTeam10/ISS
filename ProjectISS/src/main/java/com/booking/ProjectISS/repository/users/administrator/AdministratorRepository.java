package com.booking.ProjectISS.repository.users.administrator;

import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Repository
//public class AdministratorRepository implements IAdministratorRepository {
//
//    private final List<Administrator> administrators=new ArrayList<Administrator>();
//    private IUserRepository userRepository;
//
//    public AdministratorRepository(){
//        loadAll();
//    }
//
//    public Collection<Administrator> loadAll(){
//        administrators.add(new Administrator(100L,"abcd","efgh"));
//        return administrators;
//    }
//    @Override
//    public Administrator findOne(Long id) {
//        for(Administrator a:administrators){
//            if(a.getId()==id){
//                return a;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<Administrator> findAll() {
//        return administrators;
//    }
//    @Override
//    public void delete(Long id) {
//        administrators.removeIf(person -> person.getId() == id);
//    }
//
//    @Override
//    public Administrator create(Administrator administrator) {
//        return new Administrator();
////        Long id = administrator.getId();
////
////        if (id == null) {
////            id = userRepository.getCounter().incrementAndGet();
////            administrator.setId(id);
////        }
////        this.administrators.add(administrator);
////        return administrator;
//    }
//
//}
