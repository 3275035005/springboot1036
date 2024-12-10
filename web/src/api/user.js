import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info?token='+token,
    method: 'get'
  })
}

export function pageQuery(page, limit, data) {
  return request({
    url: `/user/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/user/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/user/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/user/update`,
    method: 'put',
    data: data
  })
}


export function getUserAll() {
  return request({
    url: '/user/getUserAll',
    method: 'get'
  })
}
export function updatePassword(data) {
  return request({
    url: `/user/updatePassword`,
    method: 'post',
    data: data
  })
}

export function resetPassword(id){
  return request({
    url: `/user/resetPassword/`+id,
    method: 'post'
  })
}

export function getHome(){
  return request({
    url: `/user/getHome`,
    method: 'get'
  })
}
