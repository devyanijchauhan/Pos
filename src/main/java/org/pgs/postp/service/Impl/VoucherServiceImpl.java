package org.pgs.postp.service.Impl;


import org.pgs.postp.dto.VoucherDTO;
import org.pgs.postp.mapper.VoucherMapper;
import org.pgs.postp.model.VoucherModel;
import org.pgs.postp.repository.VoucherRepository;
import org.pgs.postp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        List<VoucherModel> voucherModels = voucherRepository.findAll();
        return voucherModels.stream()
                .map(voucherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherDTO getVoucherById(Long id) {
        VoucherModel voucherModel = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        return voucherMapper.toDTO(voucherModel);

    }

    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        VoucherModel voucherModel = voucherMapper.toEntity(voucherDTO);
        VoucherModel savedVoucherModel = voucherRepository.save(voucherModel);
        return voucherMapper.toDTO(savedVoucherModel);
    }

    @Override
    public VoucherDTO updateVoucher(Long id, VoucherDTO voucherDTO) {
//        if (voucherRepository.existsById(id)) {
//            voucherDTO.setVoucherID(id);
//            return voucherMapper.toDTO(voucherRepository.save(voucherMapper.toEntity(voucherDTO)));
//        }
//        return null;

        VoucherModel existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));

        if (voucherDTO.getVoucherType() != null) {
            existingVoucher.setVoucherType(voucherDTO.getVoucherType());
        }

        if (voucherDTO.getVoucherDuration() != null) {
            existingVoucher.setVoucherDuration(voucherDTO.getVoucherDuration());
        }

        if (voucherDTO.getVoucherDuration() != null) {
            existingVoucher.setVoucherDuration(voucherDTO.getVoucherDuration());
        }

        if (voucherDTO.getVoucherCount() != null) {
            existingVoucher.setVoucherCount(voucherDTO.getVoucherCount());
        }

        if (voucherDTO.getDiscountAmount() != null) {
            existingVoucher.setDiscountAmount(voucherDTO.getDiscountAmount());
        }
        // Update Properties here
        VoucherModel updateVoucher = voucherRepository.save(existingVoucher);
        return voucherMapper.toDTO(updateVoucher);
    }

    @Override
    public void deleteVoucher(Long id) {

        if (!voucherRepository.existsById(id)) {
            throw new RuntimeException("Voucher not found with id: " +id);
        }
        voucherRepository.deleteById(id);
    }
}
