import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/orderDetail/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function complete(data) {
  return request({
    url: `/orderDetail/complete`,
    method: 'post',
    data: data
  })
}

