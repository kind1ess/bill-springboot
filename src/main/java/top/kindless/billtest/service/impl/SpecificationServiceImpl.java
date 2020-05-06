package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Specification;
import top.kindless.billtest.repository.SpecificationRepository;
import top.kindless.billtest.service.SpecificationService;

import java.util.Optional;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    private final SpecificationRepository specificationRepository;

    public SpecificationServiceImpl(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Override
    public Specification findById(Integer id) {
        Optional<Specification> specificationOptional = specificationRepository.findById(id);
        if (!specificationOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return specificationOptional.get();
    }
}
