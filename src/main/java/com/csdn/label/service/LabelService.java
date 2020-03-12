package com.csdn.label.service;

import com.csdn.label.dao.LabelDao;
import com.csdn.label.pojo.Label;
import com.csdn.utils.IdWorker;
import com.csdn.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;


    public List<Label> findAll()
    {
        return labelDao.findAll();
    }

    public Label findById(String id)
    {
        return labelDao.findById(id).get();
    }

    public void add(Label label){
        label.setId(idWorker.nextId()+"");
         labelDao.save(label);
    }

    public void update(Label label)
    {
        labelDao.save(label);
    }

    public void delete(String id)
    {
        labelDao.deleteById(id);
    }

    public Page<Label> findAllToPage()
    {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort orders = new Sort(order);
        PageRequest pageRequest = new PageRequest(0, 3, orders);
        return labelDao.findAll(pageRequest);
    }

    /**
     *带条件的查询
     *key：字段的名字， value：字段的值
     *
     * 输入java
     * key：labelname value：java
     */
   public List<Label> findSearch(Map searchMap)
    {
        Specification specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /***
     * 带条件的查询 分页
     */
    public Page<Label> findAllByPage(Map searchMap,int page,int pageSize)
    {
        Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
        return labelDao.findAll(specification,pageRequest);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Specification createSpecification(Map searchMap)
    {
       return new Specification() {
           @Override
           public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
               ArrayList<Predicate> predicates = new ArrayList<>();
               if (searchMap.get("labelname") != null && !"".equals(searchMap.get("labelname")))
               {
                  predicates.add(cb.like(root.get("labelname").as(String.class),"%"+searchMap.get("labelname")+"%"));
               }

               if(searchMap.get("fans") != null && !"".equals(searchMap.get("fans")))
               {
                   predicates.add(cb.equal(root.get("fans").as(String.class),(String)searchMap.get("fans")));
               }
               System.out.println("count"+predicates.size());
               return cb.and(predicates.toArray(new Predicate[predicates.size()]));
           }
       };
    }

}
