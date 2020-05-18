package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Specification;
import top.kindless.billtest.repository.SpecificationRepository;
import top.kindless.billtest.service.SpecificationService;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "specification")
public class SpecificationServiceImpl implements SpecificationService {

    private final SpecificationRepository specificationRepository;

    public SpecificationServiceImpl(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Specification findById(Integer id) {
        Optional<Specification> specificationOptional = specificationRepository.findById(id);
        if (!specificationOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return specificationOptional.get();
    }

    @Override
    public void saveSpecification(Specification specification) {
        specificationRepository.save(specification);
    }

    @Override
    @CachePut(key = "#specification.id")
    public Specification updateSpecification(Specification specification) {
        specificationRepository.saveAndFlush(specification);
        return specification;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteSpecificationById(Integer id) {
        specificationRepository.deleteById(id);
    }
}
