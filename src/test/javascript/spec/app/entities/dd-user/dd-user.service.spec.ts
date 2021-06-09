/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { DdUser } from '@/shared/model/dd-user.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('DdUser Service', () => {
    let service: DdUserService;
    let elemDefault;

    beforeEach(() => {
      service = new DdUserService();
      elemDefault = new DdUser(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a DdUser', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a DdUser', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a DdUser', async () => {
        const returnedFromService = Object.assign(
          {
            unionid: 'BBBBBB',
            remark: 'BBBBBB',
            userid: 'BBBBBB',
            isLeaderInDepts: 'BBBBBB',
            isBoss: true,
            hiredDate: 1,
            isSenior: true,
            tel: 'BBBBBB',
            department: 'BBBBBB',
            workPlace: 'BBBBBB',
            orderInDepts: 'BBBBBB',
            mobile: 'BBBBBB',
            errmsg: 'BBBBBB',
            active: true,
            avatar: 'BBBBBB',
            isAdmin: true,
            isHide: true,
            jobnumber: 'BBBBBB',
            name: 'BBBBBB',
            extattr: 'BBBBBB',
            stateCode: 'BBBBBB',
            position: 'BBBBBB',
            roles: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a DdUser', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a DdUser', async () => {
        const patchObject = Object.assign(
          {
            unionid: 'BBBBBB',
            userid: 'BBBBBB',
            isLeaderInDepts: 'BBBBBB',
            isSenior: true,
            tel: 'BBBBBB',
            orderInDepts: 'BBBBBB',
            mobile: 'BBBBBB',
            errmsg: 'BBBBBB',
            jobnumber: 'BBBBBB',
            name: 'BBBBBB',
            extattr: 'BBBBBB',
            position: 'BBBBBB',
            roles: 'BBBBBB',
          },
          new DdUser()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a DdUser', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of DdUser', async () => {
        const returnedFromService = Object.assign(
          {
            unionid: 'BBBBBB',
            remark: 'BBBBBB',
            userid: 'BBBBBB',
            isLeaderInDepts: 'BBBBBB',
            isBoss: true,
            hiredDate: 1,
            isSenior: true,
            tel: 'BBBBBB',
            department: 'BBBBBB',
            workPlace: 'BBBBBB',
            orderInDepts: 'BBBBBB',
            mobile: 'BBBBBB',
            errmsg: 'BBBBBB',
            active: true,
            avatar: 'BBBBBB',
            isAdmin: true,
            isHide: true,
            jobnumber: 'BBBBBB',
            name: 'BBBBBB',
            extattr: 'BBBBBB',
            stateCode: 'BBBBBB',
            position: 'BBBBBB',
            roles: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of DdUser', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a DdUser', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a DdUser', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
