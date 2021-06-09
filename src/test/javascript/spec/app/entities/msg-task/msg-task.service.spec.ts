/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import MsgTaskService from '@/entities/msg-task/msg-task.service';
import { MsgTask } from '@/shared/model/msg-task.model';

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
  describe('MsgTask Service', () => {
    let service: MsgTaskService;
    let elemDefault;

    beforeEach(() => {
      service = new MsgTaskService();
      elemDefault = new MsgTask(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
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

      it('should create a MsgTask', async () => {
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

      it('should not create a MsgTask', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MsgTask', async () => {
        const returnedFromService = Object.assign(
          {
            useridList: 'BBBBBB',
            taskId: 1,
            rspMsg: 'BBBBBB',
            status: 1,
            progressInPercent: 1,
            invalidUserIdList: 'BBBBBB',
            forbiddenUserIdList: 'BBBBBB',
            failedUserIdList: 'BBBBBB',
            readUserIdList: 'BBBBBB',
            unreadUserIdList: 'BBBBBB',
            invalidDeptIdList: 'BBBBBB',
            withdraw: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MsgTask', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MsgTask', async () => {
        const patchObject = Object.assign(
          {
            useridList: 'BBBBBB',
            taskId: 1,
            status: 1,
            forbiddenUserIdList: 'BBBBBB',
            failedUserIdList: 'BBBBBB',
            readUserIdList: 'BBBBBB',
            invalidDeptIdList: 'BBBBBB',
            withdraw: true,
          },
          new MsgTask()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MsgTask', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MsgTask', async () => {
        const returnedFromService = Object.assign(
          {
            useridList: 'BBBBBB',
            taskId: 1,
            rspMsg: 'BBBBBB',
            status: 1,
            progressInPercent: 1,
            invalidUserIdList: 'BBBBBB',
            forbiddenUserIdList: 'BBBBBB',
            failedUserIdList: 'BBBBBB',
            readUserIdList: 'BBBBBB',
            unreadUserIdList: 'BBBBBB',
            invalidDeptIdList: 'BBBBBB',
            withdraw: true,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MsgTask', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MsgTask', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MsgTask', async () => {
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
